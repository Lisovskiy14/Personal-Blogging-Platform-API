package com.example.blogging.dto.post;

import lombok.Value;

import java.util.List;

@Value
public class PostListResponseDto {
    List<PostResponseDto> posts;
}
