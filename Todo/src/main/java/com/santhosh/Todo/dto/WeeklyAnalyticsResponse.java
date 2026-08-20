package com.santhosh.Todo.dto;

import java.util.List;

public class WeeklyAnalyticsResponse {

    private List<DailyTaskCompletion> taskCompletion;
    private List<DailyLearningHours> learningHours;

    public WeeklyAnalyticsResponse() {
    }

    public WeeklyAnalyticsResponse(List<DailyTaskCompletion> taskCompletion, List<DailyLearningHours> learningHours) {
        this.taskCompletion = taskCompletion;
        this.learningHours = learningHours;
    }

    public List<DailyTaskCompletion> getTaskCompletion() {
        return taskCompletion;
    }

    public void setTaskCompletion(List<DailyTaskCompletion> taskCompletion) {
        this.taskCompletion = taskCompletion;
    }

    public List<DailyLearningHours> getLearningHours() {
        return learningHours;
    }

    public void setLearningHours(List<DailyLearningHours> learningHours) {
        this.learningHours = learningHours;
    }
}
