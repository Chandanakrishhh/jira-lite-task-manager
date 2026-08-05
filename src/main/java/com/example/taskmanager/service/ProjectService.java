package com.example.taskmanager.service;

import com.example.taskmanager.model.Project;
import com.example.taskmanager.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
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
}