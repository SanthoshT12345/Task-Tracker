package com.santhosh.Todo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.santhosh.Todo.dto.DashboardResponse;
import com.santhosh.Todo.entity.LearningGoal;
import com.santhosh.Todo.entity.TaskStatus;
import com.santhosh.Todo.entity.User;
import com.santhosh.Todo.exception.UserNotFoundException;
import com.santhosh.Todo.repository.LearningGoalRepository;
import com.santhosh.Todo.repository.LearningSessionRepository;
import com.santhosh.Todo.repository.TaskRepository;
import com.santhosh.Todo.repository.UserRepository;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final LearningGoalRepository learningGoalRepository;
    private final LearningSessionRepository learningSessionRepository;

    public DashboardServiceImpl(TaskRepository taskRepository,
                                UserRepository userRepository,
                                LearningGoalRepository learningGoalRepository,
                                LearningSessionRepository learningSessionRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.learningGoalRepository = learningGoalRepository;
        this.learningSessionRepository = learningSessionRepository;
    }

    @Override
    public DashboardResponse getDashboardData() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        long totalTasks = taskRepository.countByUser(user);

        long completedTasks =
                taskRepository.countByUserAndStatus(
                        user,
                        TaskStatus.COMPLETED
                );

        long pendingTasks =
                taskRepository.countByUserAndStatus(
                        user,
                        TaskStatus.PENDING
                );

        long todayTasks =
                taskRepository.countByUserAndDueDate(user, LocalDate.now());

        // Learning stats
        Long totalMinutes = learningSessionRepository.sumDurationByUser(user);
        double totalLearningHours = (totalMinutes != null ? totalMinutes : 0L) / 60.0;
        totalLearningHours = Math.round(totalLearningHours * 10.0) / 10.0;

        Long todayMinutes = learningSessionRepository.sumDurationByUserAndSessionDate(user, LocalDate.now());
        double todayLearningHours = (todayMinutes != null ? todayMinutes : 0L) / 60.0;
        todayLearningHours = Math.round(todayLearningHours * 10.0) / 10.0;

        long activeGoals = learningGoalRepository.countByUser(user);

        // Overall progress across active goals
        List<LearningGoal> goals = learningGoalRepository.findByUser(user);
        double totalTarget = 0.0;
        double totalCompleted = 0.0;
        for (LearningGoal goal : goals) {
            totalTarget += goal.getTargetHours();
            Long minutes = learningSessionRepository.sumDurationByGoal(goal);
            totalCompleted += (minutes != null ? minutes : 0L) / 60.0;
        }

        double overallLearningProgress = totalTarget > 0
                ? Math.min(100.0, Math.round((totalCompleted / totalTarget) * 1000.0) / 10.0)
                : 0.0;

        return new DashboardResponse(
                totalTasks,
                completedTasks,
                pendingTasks,
                todayTasks,
                totalLearningHours,
                todayLearningHours,
                activeGoals,
                overallLearningProgress
        );
    }
}