package com.example.taskmanager.service;

import com.example.taskmanager.model.Comment;
import com.example.taskmanager.model.Project;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.CommentRepository;
import com.example.taskmanager.repository.ProjectRepository;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository, CommentRepository commentRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.commentRepository = commentRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Project with id " + id + " not found"));
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new IllegalStateException("Project with id " + id + " not found");
        }

        List<Task> tasks = taskRepository.findByProjectId(id);

        for (Task task : tasks) {
            List<Comment> comments = commentRepository.findByTaskId(task.getId());
            commentRepository.deleteAll(comments);
        }

        taskRepository.deleteAll(tasks);
        projectRepository.deleteById(id);
    }
}