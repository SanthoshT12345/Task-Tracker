package com.santhosh.Todo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class LearningGoalRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @Min(value = 1, message = "Target hours must be greater than 0")
    private double targetHours;

    public LearningGoalRequest() {
    }

    public LearningGoalRequest(String title, String description, double targetHours) {
        this.title = title;
        this.description = description;
        this.targetHours = targetHours;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTargetHours() {
        return targetHours;
    }

    public void setTargetHours(double targetHours) {
        this.targetHours = targetHours;
    }
}
