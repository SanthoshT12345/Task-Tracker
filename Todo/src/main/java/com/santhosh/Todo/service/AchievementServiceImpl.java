package com.santhosh.Todo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.santhosh.Todo.dto.AchievementResponse;
import com.santhosh.Todo.entity.LearningGoal;
import com.santhosh.Todo.entity.LearningSession;
import com.santhosh.Todo.entity.Task;
import com.santhosh.Todo.entity.TaskStatus;
import com.santhosh.Todo.entity.User;
import com.santhosh.Todo.exception.UserNotFoundException;
import com.santhosh.Todo.repository.LearningGoalRepository;
import com.santhosh.Todo.repository.LearningSessionRepository;
import com.santhosh.Todo.repository.TaskRepository;
import com.santhosh.Todo.repository.UserRepository;

@Service
public class AchievementServiceImpl implements AchievementService {

    private final TaskRepository taskRepository;
    private final LearningSessionRepository learningSessionRepository;
    private final LearningGoalRepository learningGoalRepository;
    private final UserRepository userRepository;

    public AchievementServiceImpl(TaskRepository taskRepository,
                                  LearningSessionRepository learningSessionRepository,
                                  LearningGoalRepository learningGoalRepository,
                                  UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.learningSessionRepository = learningSessionRepository;
        this.learningGoalRepository = learningGoalRepository;
        this.userRepository = userRepository;
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public List<AchievementResponse> getAchievements() {
        User user = getAuthenticatedUser();

        // 1. Gather stats
        long completedTasks = taskRepository.countByUserAndStatus(user, TaskStatus.COMPLETED);

        List<LearningSession> allSessions = learningSessionRepository.findByUser(user);
        long sessionsCount = allSessions.size();
        double totalLearningHours = allSessions.stream().mapToDouble(LearningSession::getDurationMinutes).sum() / 60.0;

        // Longest Streak
        List<Task> allCompletedTasks = taskRepository.findByUserAndStatus(user, TaskStatus.COMPLETED);
        Set<LocalDate> activeDates = new TreeSet<>();
        for (Task task : allCompletedTasks) {
            if (task.getUpdatedAt() != null) {
                activeDates.add(task.getUpdatedAt().toLocalDate());
            }
        }
        for (LearningSession session : allSessions) {
            if (session.getSessionDate() != null) {
                activeDates.add(session.getSessionDate());
            }
        }

        long longestStreak = 0;
        if (!activeDates.isEmpty()) {
            List<LocalDate> sortedDates = new ArrayList<>(activeDates);
            long tempStreak = 0;
            LocalDate prevDate = null;
            for (LocalDate date : sortedDates) {
                if (prevDate == null) {
                    tempStreak = 1;
                } else if (date.equals(prevDate.plusDays(1))) {
                    tempStreak++;
                } else {
                    tempStreak = 1;
                }
                longestStreak = Math.max(longestStreak, tempStreak);
                prevDate = date;
            }
        }

        // First goal completed
        List<LearningGoal> goals = learningGoalRepository.findByUser(user);
        boolean firstGoalCompleted = false;
        for (LearningGoal goal : goals) {
            Long minutes = learningSessionRepository.sumDurationByGoal(goal);
            double completedHours = (minutes != null ? minutes : 0L) / 60.0;
            if (goal.getTargetHours() > 0 && completedHours >= goal.getTargetHours()) {
                firstGoalCompleted = true;
                break;
            }
        }

        // 2. Build Achievements list
        List<AchievementResponse> list = new ArrayList<>();

        list.add(new AchievementResponse(
                "FIRST_TASK",
                "First Step",
                "Complete your first task",
                completedTasks >= 1
        ));

        list.add(new AchievementResponse(
                "10_TASKS_COMPLETED",
                "Task Master",
                "Complete 10 tasks",
                completedTasks >= 10
        ));

        list.add(new AchievementResponse(
                "50_TASKS_COMPLETED",
                "Productivity Legend",
                "Complete 50 tasks",
                completedTasks >= 50
        ));

        list.add(new AchievementResponse(
                "FIRST_LEARNING_SESSION",
                "First Lesson",
                "Record your first learning session",
                sessionsCount >= 1
        ));

        list.add(new AchievementResponse(
                "10_LEARNING_HOURS",
                "Knowledge Seeker",
                "Accumulate 10 hours of learning",
                totalLearningHours >= 10.0
        ));

        list.add(new AchievementResponse(
                "50_LEARNING_HOURS",
                "Master Scholar",
                "Accumulate 50 hours of learning",
                totalLearningHours >= 50.0
        ));

        list.add(new AchievementResponse(
                "7_DAY_STREAK",
                "Week Warrior",
                "Maintain a 7-day learning/task streak",
                longestStreak >= 7
        ));

        list.add(new AchievementResponse(
                "30_DAY_STREAK",
                "Month Titan",
                "Maintain a 30-day learning/task streak",
                longestStreak >= 30
        ));

        list.add(new AchievementResponse(
                "FIRST_GOAL_COMPLETED",
                "Goal Achiever",
                "Reach target hours on your first learning goal",
                firstGoalCompleted
        ));

        return list;
    }
}
