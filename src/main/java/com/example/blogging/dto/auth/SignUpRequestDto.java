package com.example.blogging.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

@Value
public class SignUpRequestDto {
    @NotBlank(message = "is required")
    @Pattern(
            regexp = "^[a-zA-Z0-9_]{3,20}$",
            message = "must be between 3 and 20 characters and contain only letters, numbers and underscores."
    )
    String username;

    @NotBlank(message = "is required")
    @Pattern(
            regexp = "^[a-zA-Z0-9]+$",
            message = "must contain only letters and numbers."
    )
    String password;
}
