package com.santhosh.Todo.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LearningSessionRequest {

    @NotBlank(message = "Topic is required")
    private String topic;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int durationMinutes;

    @NotNull(message = "Session date is required")
    private LocalDate sessionDate;

    private Long goalId; // optional

    public LearningSessionRequest() {
    }

    public LearningSessionRequest(String topic, int durationMinutes, LocalDate sessionDate, Long goalId) {
        this.topic = topic;
        this.durationMinutes = durationMinutes;
        this.sessionDate = sessionDate;
        this.goalId = goalId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public LocalDate getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(LocalDate sessionDate) {
        this.sessionDate = sessionDate;
    }

    public Long getGoalId() {
        return goalId;
    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }
}
