package com.santhosh.Todo.dto;

public class DailyTaskCompletion {

    private String date;
    private long completedTasks;

    public DailyTaskCompletion() {
    }

    public DailyTaskCompletion(String date, long completedTasks) {
        this.date = date;
        this.completedTasks = completedTasks;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(long completedTasks) {
        this.completedTasks = completedTasks;
    }
}
