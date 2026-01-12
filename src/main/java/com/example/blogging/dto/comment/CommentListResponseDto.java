package com.example.blogging.dto.comment;

import lombok.Value;

import java.util.List;

@Value
public class CommentListResponseDto {
    List<CommentResponseDto> comments;
}
