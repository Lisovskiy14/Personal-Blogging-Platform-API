package com.example.blogging.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.Set;
import java.util.UUID;

@Value
public class PostRequestDto {

    @NotBlank(message = "is required")
    UUID authorId;

    @NotBlank(message = "is required")
    @Size(min = 3, max = 50, message = "must be between 3 and 50 characters")
    String title;

    @NotBlank(message = "is required")
    @Size(min = 5, max = 200, message = "must be between 5 and 200 characters")
    String content;

    String imageUrl;

    Set<String> tagSlugs;
}
