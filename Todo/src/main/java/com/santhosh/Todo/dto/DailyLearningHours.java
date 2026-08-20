package com.santhosh.Todo.dto;

public class DailyLearningHours {

    private String date;
    private double hours;

    public DailyLearningHours() {
    }

    public DailyLearningHours(String date, double hours) {
        this.date = date;
        this.hours = hours;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }
}
