package com.santhosh.Todo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santhosh.Todo.entity.Task;
import com.santhosh.Todo.entity.TaskStatus;
import com.santhosh.Todo.entity.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);
    long countByUser(User user);

    long countByUserAndStatus(User user, TaskStatus status);

    long countByUserAndDueDate(User user, LocalDate dueDate);

    List<Task> findByDueDateBeforeAndStatusNotIn(LocalDate date, List<TaskStatus> statuses);

    List<Task> findByUserAndStatus(User user, TaskStatus status);

    List<Task> findByUserAndStatusAndUpdatedAtBetween(User user, TaskStatus status, java.time.LocalDateTime start, java.time.LocalDateTime end);
}