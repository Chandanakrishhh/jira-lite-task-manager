package com.example.taskmanager.dto;

public class CommentResponseDto {

    private Long id;
    private String content;
    private Long taskId;
    private UserResponseDto author;

    public CommentResponseDto() {
    }

    public CommentResponseDto(Long id, String content, Long taskId, UserResponseDto author) {
        this.id = id;
        this.content = content;
        this.taskId = taskId;
        this.author = author;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }
    public UserResponseDto getAuthor() { return author; }
    public void setAuthor(UserResponseDto author) { this.author = author; }
}