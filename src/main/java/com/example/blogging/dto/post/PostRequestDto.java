package com.example.blogging.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;
import org.hibernate.validator.constraints.URL;

import java.util.Set;
import java.util.UUID;

@Value
public class PostRequestDto {

    @NotNull(message = "is required")
    UUID authorId;

    @NotBlank(message = "is required")
    @Size(min = 3, max = 50, message = "must be between 3 and 50 characters")
    String title;

    @Size(min = 5, max = 1000, message = "must be between 5 and 500 characters")
    String content;

    @URL(protocol = "https", message = "must be a valid URL")
    String imageUrl;

    Set<Long> tagIds;
}
