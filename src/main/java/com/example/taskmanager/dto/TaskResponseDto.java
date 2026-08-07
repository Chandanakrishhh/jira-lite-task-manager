package com.example.taskmanager.dto;

import com.example.taskmanager.enums.TaskStatus;

public class TaskResponseDto {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private Long projectId;
    private UserResponseDto assignee;

    public TaskResponseDto() {
    }

    public TaskResponseDto(Long id, String title, String description, TaskStatus status, Long projectId, UserResponseDto assignee) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.projectId = projectId;
        this.assignee = assignee;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }
    public UserResponseDto getAssignee() { return assignee; }
    public void setAssignee(UserResponseDto assignee) { this.assignee = assignee; }
}