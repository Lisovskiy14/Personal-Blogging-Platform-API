package com.example.blogging.dto.tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class TagRequestDto {

    @NotBlank(message = "is required")
    @Size(min = 3, max = 20, message = "must be between 3 and 20 characters")
    String name;

    @NotBlank(message = "is required")
    @Pattern(
            regexp = "^[a-z0-9-]{3,20}$",
            message = "must be between 3 and 20 characters and contain only lowercase letters, numbers and dashes"
    )
    String slug;
}
