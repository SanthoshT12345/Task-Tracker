package com.santhosh.Todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.santhosh.Todo.entity.Task;
import com.santhosh.Todo.entity.User;
import com.santhosh.Todo.repository.TaskRepository;
import com.santhosh.Todo.repository.UserRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // Create Task
    public Task createTask(Task task) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.setUser(user);

if (task.getPriority() == null) {
    task.setPriority(com.santhosh.Todo.entity.Priority.MEDIUM);
}

task.setStatus(com.santhosh.Todo.entity.TaskStatus.PENDING);

return taskRepository.save(task);
    }

    // Get My Tasks
    public List<Task> getMyTasks() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return taskRepository.findByUser(user);
    }

    // Get Task By Id
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    // Update Task
    public Task updateTask(Long id, Task updatedTask) {

    Task existingTask = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found"));

    Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (!(existingTask.getUser().getId()==(user.getId()))) {
        throw new RuntimeException("Unauthorized");
    }

    existingTask.setTitle(updatedTask.getTitle());
    existingTask.setDescription(updatedTask.getDescription());
    existingTask.setPriority(updatedTask.getPriority());
    existingTask.setStatus(updatedTask.getStatus());
    existingTask.setDueDate(updatedTask.getDueDate());

    return taskRepository.save(existingTask);
}

    // Delete Task
   public void deleteTask(Long id) {

    Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found"));

    Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (!(task.getUser().getId() == user.getId())) {
    throw new RuntimeException("Unauthorized");
}

    taskRepository.delete(task);

}
public Task completeTask(Long id) {

    Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found"));

    Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

   if (!(task.getUser().getId() == user.getId())) {
    throw new RuntimeException("Unauthorized");
}

    task.setStatus(com.santhosh.Todo.entity.TaskStatus.COMPLETED);

    return taskRepository.save(task);

}
}