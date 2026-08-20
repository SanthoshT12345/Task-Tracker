package com.santhosh.Todo.dto;

public class DashboardResponse {

    private long totalTasks;
    private long completedTasks;
    private long pendingTasks;
    private long todayTasks;

    private double totalLearningHours;
    private double todayLearningHours;
    private long activeGoals;
    private double overallLearningProgress;

    public DashboardResponse() {
    }

    public DashboardResponse(long totalTasks,
                              long completedTasks,
                              long pendingTasks,
                              long todayTasks,
                              double totalLearningHours,
                              double todayLearningHours,
                              long activeGoals,
                              double overallLearningProgress) {

        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.pendingTasks = pendingTasks;
        this.todayTasks = todayTasks;
        this.totalLearningHours = totalLearningHours;
        this.todayLearningHours = todayLearningHours;
        this.activeGoals = activeGoals;
        this.overallLearningProgress = overallLearningProgress;
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

    public long getTodayTasks() {
        return todayTasks;
    }

    public void setTodayTasks(long todayTasks) {
        this.todayTasks = todayTasks;
    }

    public double getTotalLearningHours() {
        return totalLearningHours;
    }

    public void setTotalLearningHours(double totalLearningHours) {
        this.totalLearningHours = totalLearningHours;
    }

    public double getTodayLearningHours() {
        return todayLearningHours;
    }

    public void setTodayLearningHours(double todayLearningHours) {
        this.todayLearningHours = todayLearningHours;
    }

    public long getActiveGoals() {
        return activeGoals;
    }

    public void setActiveGoals(long activeGoals) {
        this.activeGoals = activeGoals;
    }

    public double getOverallLearningProgress() {
        return overallLearningProgress;
    }

    public void setOverallLearningProgress(double overallLearningProgress) {
        this.overallLearningProgress = overallLearningProgress;
    }
}