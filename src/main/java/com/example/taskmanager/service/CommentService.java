package com.example.taskmanager.service;

import com.example.taskmanager.model.Comment;
import com.example.taskmanager.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Comment with id " + id + " not found"));
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }
}