package com.santhosh.Todo.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.santhosh.Todo.dto.AnalyticsOverviewResponse;
import com.santhosh.Todo.dto.DailyLearningHours;
import com.santhosh.Todo.dto.DailyTaskCompletion;
import com.santhosh.Todo.dto.WeeklyAnalyticsResponse;
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
public class AnalyticsServiceImpl implements AnalyticsService {

    private final TaskRepository taskRepository;
    private final LearningSessionRepository learningSessionRepository;
    private final LearningGoalRepository learningGoalRepository;
    private final UserRepository userRepository;

    public AnalyticsServiceImpl(TaskRepository taskRepository,
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
    public AnalyticsOverviewResponse getOverview() {
        User user = getAuthenticatedUser();

        long totalTasks = taskRepository.countByUser(user);
        long completedTasks = taskRepository.countByUserAndStatus(user, TaskStatus.COMPLETED);
        long pendingTasks = taskRepository.countByUserAndStatus(user, TaskStatus.PENDING);
        long overdueTasks = taskRepository.countByUserAndStatus(user, TaskStatus.OVERDUE);

        long completionPercentage = 0;
        if (totalTasks > 0) {
            completionPercentage = Math.min(100, Math.max(0, (completedTasks * 100) / totalTasks));
        }

        Long totalMinutes = learningSessionRepository.sumDurationByUser(user);
        double totalLearningHours = (totalMinutes != null ? totalMinutes : 0L) / 60.0;
        totalLearningHours = Math.round(totalLearningHours * 10.0) / 10.0;

        // Weekly learning hours
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        List<LearningSession> weeklySessions = learningSessionRepository.findByUserAndSessionDateBetween(user, monday, sunday);
        double weeklyLearningHours = weeklySessions.stream()
                .mapToDouble(LearningSession::getDurationMinutes)
                .sum() / 60.0;
        weeklyLearningHours = Math.round(weeklyLearningHours * 10.0) / 10.0;

        Long todayMinutes = learningSessionRepository.sumDurationByUserAndSessionDate(user, today);
        long todayLearningMinutes = todayMinutes != null ? todayMinutes : 0L;

        long activeGoals = learningGoalRepository.countByUser(user);

        // Streaks calculation
        List<Task> allCompletedTasks = taskRepository.findByUserAndStatus(user, TaskStatus.COMPLETED);
        List<LearningSession> allSessions = learningSessionRepository.findByUser(user);

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

        long currentStreak = 0;
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

            LocalDate lastActiveDate = sortedDates.get(sortedDates.size() - 1);
            if (lastActiveDate.equals(today) || lastActiveDate.equals(today.minusDays(1))) {
                currentStreak = tempStreak;
            } else {
                currentStreak = 0;
            }
        }

        return new AnalyticsOverviewResponse(
                totalTasks,
                completedTasks,
                pendingTasks,
                overdueTasks,
                completionPercentage,
                totalLearningHours,
                weeklyLearningHours,
                todayLearningMinutes,
                activeGoals,
                currentStreak,
                longestStreak
        );
    }

    @Override
    public WeeklyAnalyticsResponse getWeekly() {
        User user = getAuthenticatedUser();

        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        LocalDateTime startOfWeek = monday.atStartOfDay();
        LocalDateTime endOfWeek = sunday.atTime(23, 59, 59, 999999999);

        List<Task> weeklyCompletedTasks = taskRepository.findByUserAndStatusAndUpdatedAtBetween(user, TaskStatus.COMPLETED, startOfWeek, endOfWeek);
        List<LearningSession> weeklySessions = learningSessionRepository.findByUserAndSessionDateBetween(user, monday, sunday);

        Map<LocalDate, Long> taskCounts = weeklyCompletedTasks.stream()
                .filter(t -> t.getUpdatedAt() != null)
                .collect(Collectors.groupingBy(
                        t -> t.getUpdatedAt().toLocalDate(),
                        Collectors.counting()
                ));

        Map<LocalDate, Double> learningHoursMap = weeklySessions.stream()
                .collect(Collectors.groupingBy(
                        LearningSession::getSessionDate,
                        Collectors.summingDouble(s -> s.getDurationMinutes() / 60.0)
                ));

        List<DailyTaskCompletion> taskCompletionList = new ArrayList<>();
        List<DailyLearningHours> learningHoursList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        for (int i = 0; i < 7; i++) {
            LocalDate date = monday.plusDays(i);
            String dateStr = date.format(formatter);

            long completedCount = taskCounts.getOrDefault(date, 0L);
            taskCompletionList.add(new DailyTaskCompletion(dateStr, completedCount));

            double hours = learningHoursMap.getOrDefault(date, 0.0);
            hours = Math.round(hours * 10.0) / 10.0;
            learningHoursList.add(new DailyLearningHours(dateStr, hours));
        }

        return new WeeklyAnalyticsResponse(taskCompletionList, learningHoursList);
    }
}
