package com.santhosh.Todo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santhosh.Todo.dto.LearningGoalRequest;
import com.santhosh.Todo.dto.LearningGoalResponse;
import com.santhosh.Todo.service.LearningGoalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/learning/goals")
public class LearningGoalController {

    private final LearningGoalService learningGoalService;

    public LearningGoalController(LearningGoalService learningGoalService) {
        this.learningGoalService = learningGoalService;
    }

    @PostMapping
    public ResponseEntity<LearningGoalResponse> createGoal(@Valid @RequestBody LearningGoalRequest request) {
        return new ResponseEntity<>(learningGoalService.createGoal(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LearningGoalResponse>> getMyGoals() {
        return ResponseEntity.ok(learningGoalService.getMyGoals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LearningGoalResponse> getGoalById(@PathVariable Long id) {
        return ResponseEntity.ok(learningGoalService.getGoalById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LearningGoalResponse> updateGoal(@PathVariable Long id,
                                                            @Valid @RequestBody LearningGoalRequest request) {
        return ResponseEntity.ok(learningGoalService.updateGoal(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long id) {
        learningGoalService.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }
}
