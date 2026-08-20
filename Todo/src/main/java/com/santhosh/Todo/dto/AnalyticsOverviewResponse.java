package com.santhosh.Todo.dto;

public class AnalyticsOverviewResponse {

    private long totalTasks;
    private long completedTasks;
    private long pendingTasks;
    private long overdueTasks;
    private long completionPercentage;
    private double totalLearningHours;
    private double weeklyLearningHours;
    private long todayLearningMinutes;
    private long activeGoals;
    private long currentStreak;
    private long longestStreak;

    public AnalyticsOverviewResponse() {
    }

    public AnalyticsOverviewResponse(long totalTasks, long completedTasks, long pendingTasks, long overdueTasks,
                                     long completionPercentage, double totalLearningHours, double weeklyLearningHours,
                                     long todayLearningMinutes, long activeGoals, long currentStreak, long longestStreak) {
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.pendingTasks = pendingTasks;
        this.overdueTasks = overdueTasks;
        this.completionPercentage = completionPercentage;
        this.totalLearningHours = totalLearningHours;
        this.weeklyLearningHours = weeklyLearningHours;
        this.todayLearningMinutes = todayLearningMinutes;
        this.activeGoals = activeGoals;
        this.currentStreak = currentStreak;
        this.longestStreak = longestStreak;
    }

    public long getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(long totalTasks) {
        this.totalTasks = totalTasks;
    }

    public long getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(long completedTasks) {
        this.completedTasks = completedTasks;
    }

    public long getPendingTasks() {
        return pendingTasks;
    }

    public void setPendingTasks(long pendingTasks) {
        this.pendingTasks = pendingTasks;
    }

    public long getOverdueTasks() {
        return overdueTasks;
    }

    public void setOverdueTasks(long overdueTasks) {
        this.overdueTasks = overdueTasks;
    }

    public long getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(long completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public double getTotalLearningHours() {
        return totalLearningHours;
    }

    public void setTotalLearningHours(double totalLearningHours) {
        this.totalLearningHours = totalLearningHours;
    }

    public double getWeeklyLearningHours() {
        return weeklyLearningHours;
    }

    public void setWeeklyLearningHours(double weeklyLearningHours) {
        this.weeklyLearningHours = weeklyLearningHours;
    }

    public long getTodayLearningMinutes() {
        return todayLearningMinutes;
    }

    public void setTodayLearningMinutes(long todayLearningMinutes) {
        this.todayLearningMinutes = todayLearningMinutes;
    }

    public long getActiveGoals() {
        return activeGoals;
    }

    public void setActiveGoals(long activeGoals) {
        this.activeGoals = activeGoals;
    }

    public long getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(long currentStreak) {
        this.currentStreak = currentStreak;
    }

    public long getLongestStreak() {
        return longestStreak;
    }

    public void setLongestStreak(long longestStreak) {
        this.longestStreak = longestStreak;
    }
}
