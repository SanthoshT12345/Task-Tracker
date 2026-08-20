package com.santhosh.Todo.service;

import java.util.List;

import com.santhosh.Todo.dto.AchievementResponse;

public interface AchievementService {

    List<AchievementResponse> getAchievements();
}
