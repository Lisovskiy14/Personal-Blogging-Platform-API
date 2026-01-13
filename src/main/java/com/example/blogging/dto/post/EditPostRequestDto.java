package com.example.blogging.dto.post;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.Set;

@Value
public class EditPostRequestDto {

    @Size(min = 3, max = 50, message = "must be between 3 and 50 characters")
    String title;

    @Size(min = 3, max = 50, message = "must be between 3 and 50 characters")
    String content;

    String imageUrl;

    boolean toAddTags;

    Set<String> tagSlugs;
}
