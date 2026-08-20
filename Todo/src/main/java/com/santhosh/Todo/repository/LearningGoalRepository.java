package com.santhosh.Todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santhosh.Todo.entity.LearningGoal;
import com.santhosh.Todo.entity.User;

@Repository
public interface LearningGoalRepository extends JpaRepository<LearningGoal, Long> {

    List<LearningGoal> findByUser(User user);

    long countByUser(User user);
}
