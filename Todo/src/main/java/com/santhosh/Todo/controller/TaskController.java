package com.santhosh.Todo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

import com.santhosh.Todo.entity.Task;
import com.santhosh.Todo.service.TaskService;


@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Create Task
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    // Get Logged-in User Tasks
    @GetMapping
    public List<Task> getMyTasks() {
        return taskService.getMyTasks();
    }

    // Get Task By Id
    @GetMapping("/{id}")
    public Optional<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    // Update Task
   @PutMapping("/{id}")
public Task updateTask(@PathVariable Long id,
                       @RequestBody Task task) {

    return taskService.updateTask(id, task);

}

    // Delete Task
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);

        return "Task deleted successfully";
    }
    @PatchMapping("/{id}/complete")
public Task completeTask(@PathVariable Long id) {

    return taskService.completeTask(id);

}
}