package com.santhosh.Todo.dto;

public class AchievementResponse {

    private String code;
    private String title;
    private String description;
    private boolean earned;

    public AchievementResponse() {
    }

    public AchievementResponse(String code, String title, String description, boolean earned) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.earned = earned;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public boolean isEarned() {
        return earned;
    }

    public void setEarned(boolean earned) {
        this.earned = earned;
    }
}
