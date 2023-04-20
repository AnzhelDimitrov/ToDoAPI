package com.TODO.ToDoApp.controllers;

import com.TODO.ToDoApp.entities.Task;
import org.springframework.web.bind.annotation.*;
import com.TODO.ToDoApp.repositories.TaskRepository;

import java.util.Optional;

@RestController
public class TaskController {
    private final TaskRepository taskRepository;

    public TaskController(final TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/tasks")
    public Iterable<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/tasks/{id}")
    public Optional<Task> getTaskById(@PathVariable("id") Long id) {
        return taskRepository.findById(id);
    }

    @PostMapping("/tasks")
    public Task createNewTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    @PutMapping("/tasks/{id}")
    public Task updateTask(@PathVariable("id") Long id, @RequestBody Task task) {
        Optional<Task> taskToUpdateOptional = taskRepository.findById(id);
        if (taskToUpdateOptional.isEmpty()) {
            return null;
        }

        Task taskToUpdate = taskToUpdateOptional.get();

        if (task.getTitle() != null) {
            taskToUpdate.setTitle(task.getTitle());
        }

        if (task.getDescription() != null) {
            taskToUpdate.setDescription(task.getDescription());
        }

        if (task.isCompleted() != taskToUpdate.isCompleted()) {
            taskToUpdate.setCompleted(task.isCompleted());
        }

        return taskRepository.save(taskToUpdate);
    }

    @DeleteMapping("/tasks/{id}")
    public Task deleteTask(@PathVariable("id") long id) {
        Optional<Task> taskToDeleteOptional = taskRepository.findById(id);
        if (taskToDeleteOptional.isEmpty()) {
            return null;
        }

        Task taskToDelete = taskToDeleteOptional.get();
        taskRepository.delete(taskToDelete);

        return taskToDelete;
    }
}
