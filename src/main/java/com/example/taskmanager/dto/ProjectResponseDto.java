package com.example.taskmanager.dto;

public class ProjectResponseDto {

    private Long id;
    private String name;
    private String description;
    private UserResponseDto owner;

    public ProjectResponseDto() {
    }

    public ProjectResponseDto(Long id, String name, String description, UserResponseDto owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public UserResponseDto getOwner() { return owner; }
    public void setOwner(UserResponseDto owner) { this.owner = owner; }
}