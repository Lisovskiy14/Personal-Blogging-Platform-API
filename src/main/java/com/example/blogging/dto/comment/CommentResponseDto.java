package com.example.blogging.dto.comment;

import com.example.blogging.dto.post.PostResponseDto;
import com.example.blogging.dto.user.UserResponseDto;
import lombok.Value;

import java.util.UUID;

@Value
public class CommentResponseDto {
    UUID id;
    UserResponseDto author;
    PostResponseDto post;
    String content;
}
