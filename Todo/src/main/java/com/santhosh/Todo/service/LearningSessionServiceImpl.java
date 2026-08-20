package com.santhosh.Todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.santhosh.Todo.dto.LearningSessionRequest;
import com.santhosh.Todo.dto.LearningSessionResponse;
import com.santhosh.Todo.entity.LearningGoal;
import com.santhosh.Todo.entity.LearningSession;
import com.santhosh.Todo.entity.User;
import com.santhosh.Todo.exception.ForbiddenException;
import com.santhosh.Todo.exception.ResourceNotFoundException;
import com.santhosh.Todo.exception.UserNotFoundException;
import com.santhosh.Todo.repository.LearningGoalRepository;
import com.santhosh.Todo.repository.LearningSessionRepository;
import com.santhosh.Todo.repository.UserRepository;

@Service
public class LearningSessionServiceImpl implements LearningSessionService {

    private final LearningSessionRepository learningSessionRepository;
    private final LearningGoalRepository learningGoalRepository;
    private final UserRepository userRepository;

    public LearningSessionServiceImpl(LearningSessionRepository learningSessionRepository,
                                     LearningGoalRepository learningGoalRepository,
                                     UserRepository userRepository) {
        this.learningSessionRepository = learningSessionRepository;
        this.learningGoalRepository = learningGoalRepository;
        this.userRepository = userRepository;
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private LearningSessionResponse mapToResponse(LearningSession session) {
        Long goalId = session.getGoal() != null ? session.getGoal().getId() : null;
        String goalTitle = session.getGoal() != null ? session.getGoal().getTitle() : null;

        return new LearningSessionResponse(
                session.getId(),
                session.getTopic(),
                session.getDurationMinutes(),
                session.getSessionDate(),
                session.getCreatedAt(),
                goalId,
                goalTitle
        );
    }

    @Override
    public LearningSessionResponse createSession(LearningSessionRequest request) {
        User user = getAuthenticatedUser();

        LearningGoal goal = null;
        if (request.getGoalId() != null) {
            goal = learningGoalRepository.findById(request.getGoalId())
                    .orElseThrow(() -> new ResourceNotFoundException("Learning goal not found"));

            if (goal.getUser().getId() != user.getId()) {
                throw new ForbiddenException("Access denied: You do not own this goal");
            }
        }

        LearningSession session = new LearningSession();
        session.setTopic(request.getTopic());
        session.setDurationMinutes(request.getDurationMinutes());
        session.setSessionDate(request.getSessionDate());
        session.setGoal(goal);
        session.setUser(user);

        LearningSession savedSession = learningSessionRepository.save(session);
        return mapToResponse(savedSession);
    }

    @Override
    public List<LearningSessionResponse> getMySessions() {
        User user = getAuthenticatedUser();
        List<LearningSession> sessions = learningSessionRepository
                .findByUserOrderBySessionDateDescCreatedAtDesc(user);
        return sessions.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteSession(Long id) {
        User user = getAuthenticatedUser();
        LearningSession session = learningSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Learning session not found"));

        if (session.getUser().getId() != user.getId()) {
            throw new ForbiddenException("Access denied");
        }

        learningSessionRepository.delete(session);
    }
}
