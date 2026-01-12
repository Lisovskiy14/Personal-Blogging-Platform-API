package com.example.blogging.service;

import com.example.blogging.domain.Comment;
import com.example.blogging.dto.comment.CommentRequestDto;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    List<Comment> getAllCommentsByPostId(UUID postId);
    List<Comment> getAllCommentsByAuthorId(UUID authorId);
    Comment getCommentById(UUID id);
    Comment createComment(CommentRequestDto commentRequestDto);
    void deleteCommentById(UUID id);
}
