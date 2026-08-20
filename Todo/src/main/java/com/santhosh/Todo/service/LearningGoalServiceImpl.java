package com.santhosh.Todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.santhosh.Todo.dto.LearningGoalRequest;
import com.santhosh.Todo.dto.LearningGoalResponse;
import com.santhosh.Todo.entity.LearningGoal;
import com.santhosh.Todo.entity.User;
import com.santhosh.Todo.exception.ForbiddenException;
import com.santhosh.Todo.exception.ResourceNotFoundException;
import com.santhosh.Todo.exception.UserNotFoundException;
import com.santhosh.Todo.repository.LearningGoalRepository;
import com.santhosh.Todo.repository.LearningSessionRepository;
import com.santhosh.Todo.repository.UserRepository;

@Service
public class LearningGoalServiceImpl implements LearningGoalService {

    private final LearningGoalRepository learningGoalRepository;
    private final LearningSessionRepository learningSessionRepository;
    private final UserRepository userRepository;

    public LearningGoalServiceImpl(LearningGoalRepository learningGoalRepository,
                                  LearningSessionRepository learningSessionRepository,
                                  UserRepository userRepository) {
        this.learningGoalRepository = learningGoalRepository;
        this.learningSessionRepository = learningSessionRepository;
        this.userRepository = userRepository;
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private LearningGoalResponse mapToResponse(LearningGoal goal) {
        Long totalMinutes = learningSessionRepository.sumDurationByGoal(goal);
        double completedHours = (totalMinutes != null ? totalMinutes : 0L) / 60.0;
        double progressPercentage = goal.getTargetHours() > 0
                ? Math.min(100.0, Math.round((completedHours / goal.getTargetHours()) * 1000.0) / 10.0)
                : 0.0;
        completedHours = Math.round(completedHours * 10.0) / 10.0;

        return new LearningGoalResponse(
                goal.getId(),
                goal.getTitle(),
                goal.getDescription(),
                goal.getTargetHours(),
                completedHours,
                progressPercentage,
                goal.getCreatedAt(),
                goal.getUpdatedAt()
        );
    }

    @Override
    public LearningGoalResponse createGoal(LearningGoalRequest request) {
        User user = getAuthenticatedUser();

        LearningGoal goal = new LearningGoal();
        goal.setTitle(request.getTitle());
        goal.setDescription(request.getDescription());
        goal.setTargetHours(request.getTargetHours());
        goal.setUser(user);

        LearningGoal savedGoal = learningGoalRepository.save(goal);
        return mapToResponse(savedGoal);
    }

    @Override
    public List<LearningGoalResponse> getMyGoals() {
        User user = getAuthenticatedUser();
        List<LearningGoal> goals = learningGoalRepository.findByUser(user);
        return goals.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public LearningGoalResponse getGoalById(Long id) {
        User user = getAuthenticatedUser();
        LearningGoal goal = learningGoalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Learning goal not found"));

        if (goal.getUser().getId() != user.getId()) {
            throw new ForbiddenException("Access denied");
        }

        return mapToResponse(goal);
    }

    @Override
    public LearningGoalResponse updateGoal(Long id, LearningGoalRequest request) {
        User user = getAuthenticatedUser();
        LearningGoal goal = learningGoalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Learning goal not found"));

        if (goal.getUser().getId() != user.getId()) {
            throw new ForbiddenException("Access denied");
        }

        goal.setTitle(request.getTitle());
        goal.setDescription(request.getDescription());
        goal.setTargetHours(request.getTargetHours());

        LearningGoal updatedGoal = learningGoalRepository.save(goal);
        return mapToResponse(updatedGoal);
    }

    @Override
    public void deleteGoal(Long id) {
        User user = getAuthenticatedUser();
        LearningGoal goal = learningGoalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Learning goal not found"));

        if (goal.getUser().getId() != user.getId()) {
            throw new ForbiddenException("Access denied");
        }

        learningGoalRepository.delete(goal);
    }
}
