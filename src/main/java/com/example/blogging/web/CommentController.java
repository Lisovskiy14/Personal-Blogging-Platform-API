package com.example.blogging.web;

import com.example.blogging.domain.Comment;
import com.example.blogging.dto.comment.CommentListResponseDto;
import com.example.blogging.dto.comment.CommentRequestDto;
import com.example.blogging.dto.comment.CommentResponseDto;
import com.example.blogging.service.CommentService;
import com.example.blogging.web.mapper.CommentWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;
    private final CommentWebMapper commentWebMapper;

    @GetMapping
    public ResponseEntity<CommentListResponseDto> getAllCommentsByPostOrAuthorId(
            @RequestParam(required = false) UUID postId,
            @RequestParam(required = false) UUID authorId
    ) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new CommentListResponseDto(
                        commentService.getAllComments(postId, authorId).stream()
                                .map(commentWebMapper::toResponseDto)
                                .toList()
                ));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable UUID commentId) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(commentWebMapper.toResponseDto(commentService.getCommentById(commentId)));
    }

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@Valid @RequestBody CommentRequestDto commentRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(commentWebMapper.toResponseDto(commentService.createComment(commentRequestDto)));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable UUID commentId) {
        commentService.deleteCommentById(commentId);
        return ResponseEntity.noContent().build();
    }
}
