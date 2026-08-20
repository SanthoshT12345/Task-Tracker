package com.santhosh.Todo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.santhosh.Todo.entity.Task;
import com.santhosh.Todo.entity.TaskStatus;
import com.santhosh.Todo.repository.TaskRepository;

@Component
public class OverdueTaskScheduler {

    private final TaskRepository taskRepository;

    public OverdueTaskScheduler(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Scheduled(fixedRate = 3600000, initialDelay = 5000)
    public void markOverdueTasks() {

        List<TaskStatus> excludedStatuses = List.of(
                TaskStatus.COMPLETED,
                TaskStatus.OVERDUE
        );

        List<Task> overdueTasks = taskRepository
                .findByDueDateBeforeAndStatusNotIn(LocalDate.now(), excludedStatuses);

        for (Task task : overdueTasks) {
            task.setStatus(TaskStatus.OVERDUE);
        }

        if (!overdueTasks.isEmpty()) {
            taskRepository.saveAll(overdueTasks);
        }
    }
}
