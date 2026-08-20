package com.santhosh.Todo.dto;

import java.time.LocalDate;

public class TaskResponse {

    private Long id;

    private String title;

    private String description;

    private LocalDate dueDate;

    private boolean completed;
    public TaskResponse() {
    }
    // getters and setters
    public TaskResponse(Long id,
                    String title,
                    String description,
                    LocalDate dueDate,
                    boolean completed) {

    this.id = id;
    this.title = title;
    this.description = description;
    this.dueDate = dueDate;
    this.completed = completed;
}
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}