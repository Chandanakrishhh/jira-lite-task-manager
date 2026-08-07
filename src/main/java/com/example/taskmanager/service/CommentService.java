package com.example.taskmanager.service;

import com.example.taskmanager.dto.CommentResponseDto;
import com.example.taskmanager.dto.UserResponseDto;
import com.example.taskmanager.exception.ResourceNotFoundException;
import com.example.taskmanager.model.Comment;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.CommentRepository;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public List<CommentResponseDto> getAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public CommentResponseDto getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found"));
        return toDto(comment);
    }

    public CommentResponseDto createComment(Comment comment) {
        Comment saved = commentRepository.save(comment);
        return toDto(saved);
    }

    private CommentResponseDto toDto(Comment comment) {
        User fullAuthor = userRepository.findById(comment.getAuthor().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + comment.getAuthor().getId() + " not found"));

        UserResponseDto authorDto = new UserResponseDto(fullAuthor.getId(), fullAuthor.getUsername(), fullAuthor.getEmail(), fullAuthor.getRole());
        return new CommentResponseDto(comment.getId(), comment.getContent(), comment.getTask().getId(), authorDto);
    }
}