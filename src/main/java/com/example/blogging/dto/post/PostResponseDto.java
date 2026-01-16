package com.example.blogging.dto.post;

import com.example.blogging.dto.comment.CommentListResponseDto;
import com.example.blogging.dto.tag.TagSetResponseDto;
import com.example.blogging.dto.user.UserResponseDto;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class PostResponseDto {
    UUID id;
    UserResponseDto author;
    String title;
    String content;
    String imageUrl;
    TagSetResponseDto tags;
    CommentListResponseDto comments;
    LocalDateTime createdAt;
}
