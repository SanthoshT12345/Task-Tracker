package com.santhosh.Todo.service;

import java.util.List;

import com.santhosh.Todo.dto.LearningGoalRequest;
import com.santhosh.Todo.dto.LearningGoalResponse;

public interface LearningGoalService {

    LearningGoalResponse createGoal(LearningGoalRequest request);

    List<LearningGoalResponse> getMyGoals();

    LearningGoalResponse getGoalById(Long id);

    LearningGoalResponse updateGoal(Long id, LearningGoalRequest request);

    void deleteGoal(Long id);
}
