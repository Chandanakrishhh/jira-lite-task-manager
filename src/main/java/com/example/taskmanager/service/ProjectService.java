package com.example.taskmanager.service;

import com.example.taskmanager.dto.ProjectResponseDto;
import com.example.taskmanager.dto.UserResponseDto;
import com.example.taskmanager.exception.ResourceNotFoundException;
import com.example.taskmanager.model.Comment;
import com.example.taskmanager.model.Project;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.CommentRepository;
import com.example.taskmanager.repository.ProjectRepository;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository,
                          CommentRepository commentRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public List<ProjectResponseDto> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ProjectResponseDto getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project with id " + id + " not found"));
        return toDto(project);
    }

    public ProjectResponseDto createProject(Project project) {
        Project saved = projectRepository.save(project);
        return toDto(saved);
    }

    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project with id " + id + " not found");
        }

        List<Task> tasks = taskRepository.findByProjectId(id);

        for (Task task : tasks) {
            List<Comment> comments = commentRepository.findByTaskId(task.getId());
            commentRepository.deleteAll(comments);
        }

        taskRepository.deleteAll(tasks);
        projectRepository.deleteById(id);
    }

    private ProjectResponseDto toDto(Project project) {
        User fullOwner = userRepository.findById(project.getOwner().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + project.getOwner().getId() + " not found"));

        UserResponseDto ownerDto = new UserResponseDto(
                fullOwner.getId(),
                fullOwner.getUsername(),
                fullOwner.getEmail(),
                fullOwner.getRole()
        );
        return new ProjectResponseDto(project.getId(), project.getName(), project.getDescription(), ownerDto);
    }
}