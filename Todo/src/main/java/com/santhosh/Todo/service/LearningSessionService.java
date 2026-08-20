package com.santhosh.Todo.service;

import java.util.List;

import com.santhosh.Todo.dto.LearningSessionRequest;
import com.santhosh.Todo.dto.LearningSessionResponse;

public interface LearningSessionService {

    LearningSessionResponse createSession(LearningSessionRequest request);

    List<LearningSessionResponse> getMySessions();

    void deleteSession(Long id);
}
