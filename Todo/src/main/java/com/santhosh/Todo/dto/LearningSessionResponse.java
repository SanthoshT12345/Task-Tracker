package com.santhosh.Todo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LearningSessionResponse {

    private Long id;
    private String topic;
    private int durationMinutes;
    private LocalDate sessionDate;
    private LocalDateTime createdAt;
    private Long goalId;
    private String goalTitle;

    public LearningSessionResponse() {
    }

    public LearningSessionResponse(Long id, String topic, int durationMinutes, LocalDate sessionDate,
                                  LocalDateTime createdAt, Long goalId, String goalTitle) {
        this.id = id;
        this.topic = topic;
        this.durationMinutes = durationMinutes;
        this.sessionDate = sessionDate;
        this.createdAt = createdAt;
        this.goalId = goalId;
        this.goalTitle = goalTitle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getGoalId() {
        return goalId;
    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }

    public String getGoalTitle() {
        return goalTitle;
    }

    public void setGoalTitle(String goalTitle) {
        this.goalTitle = goalTitle;
    }
}
