package com.santhosh.Todo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santhosh.Todo.dto.LearningSessionRequest;
import com.santhosh.Todo.dto.LearningSessionResponse;
import com.santhosh.Todo.service.LearningSessionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/learning/sessions")
public class LearningSessionController {

    private final LearningSessionService learningSessionService;

    public LearningSessionController(LearningSessionService learningSessionService) {
        this.learningSessionService = learningSessionService;
    }

    @PostMapping
    public ResponseEntity<LearningSessionResponse> createSession(@Valid @RequestBody LearningSessionRequest request) {
        return new ResponseEntity<>(learningSessionService.createSession(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LearningSessionResponse>> getMySessions() {
        return ResponseEntity.ok(learningSessionService.getMySessions());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        learningSessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}
