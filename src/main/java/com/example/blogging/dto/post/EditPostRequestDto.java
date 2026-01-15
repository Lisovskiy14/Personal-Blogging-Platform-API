package com.example.blogging.dto.post;

import jakarta.validation.constraints.Size;
import lombok.Value;
import org.hibernate.validator.constraints.URL;

import java.util.Set;

@Value
public class EditPostRequestDto {

    @Size(min = 3, max = 50, message = "must be between 3 and 50 characters")
    String title;

    @Size(min = 3, max = 1000, message = "must be between 3 and 50 characters")
    String content;

    @URL(protocol = "https", message = "must be a valid URL")
    String imageUrl;

    boolean toAddTags;

    Set<Long> tagIds;
}
