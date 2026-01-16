package com.example.blogging.web;

import com.example.blogging.domain.Post;
import com.example.blogging.dto.post.EditPostRequestDto;
import com.example.blogging.dto.post.PostListResponseDto;
import com.example.blogging.dto.post.PostRequestDto;
import com.example.blogging.dto.post.PostResponseDto;
import com.example.blogging.service.PostService;
import com.example.blogging.web.mapper.PostWebMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;
    private final PostWebMapper postWebMapper;

    @GetMapping
    public ResponseEntity<PostListResponseDto> getAllPosts(
            @RequestParam(required = false) UUID authorId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Set<Long> tagIds
    ) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new PostListResponseDto(
                        postService.getAllPosts(authorId, title, tagIds).stream()
                                .map(postWebMapper::toResponseDto)
                                .toList()
                ));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable UUID postId) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(postWebMapper.toResponseDto(postService.getPostById(postId)));
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostRequestDto postRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(postWebMapper.toResponseDto(postService.createPost(postRequestDto)));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponseDto> editPostById(
            @Valid @RequestBody EditPostRequestDto editPostRequestDto,
            @PathVariable UUID postId
    ) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(postWebMapper.toResponseDto(
                        postService.editPost(editPostRequestDto, postId)));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePostById(@PathVariable UUID postId) {
        postService.deletePostById(postId);
        return ResponseEntity.noContent().build();
    }
}
