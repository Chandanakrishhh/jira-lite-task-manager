package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskResponseDto;
import com.example.taskmanager.dto.UserResponseDto;
import com.example.taskmanager.exception.ResourceNotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TaskResponseDto> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public TaskResponseDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
        return toDto(task);
    }

    public TaskResponseDto createTask(Task task) {
        Task saved = taskRepository.save(task);
        return toDto(saved);
    }

    private TaskResponseDto toDto(Task task) {
        UserResponseDto assigneeDto = null;
        if (task.getAssignee() != null) {
            User fullAssignee = userRepository.findById(task.getAssignee().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User with id " + task.getAssignee().getId() + " not found"));
            assigneeDto = new UserResponseDto(fullAssignee.getId(), fullAssignee.getUsername(), fullAssignee.getEmail(), fullAssignee.getRole());
        }
        return new TaskResponseDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getProject().getId(),
                assigneeDto
        );
    }
}