package com.example.blogging.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.UUID;

@Value
public class CommentRequestDto {

    @NotBlank(message = "is required")
    UUID authorId;

    @NotBlank(message = "is required")
    UUID postId;

    @NotBlank(message = "is required")
    @Size(min = 1, max = 50, message = "must be between 1 and 50 characters")
    String content;
}
