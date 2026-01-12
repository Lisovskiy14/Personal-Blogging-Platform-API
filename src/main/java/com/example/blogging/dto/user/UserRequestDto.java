package com.example.blogging.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class UserRequestDto {
    @NotBlank(message = "is required")
    @Size(min = 3, max = 20, message = "must be between 3 and 20 characters")
    String username;

    @NotBlank(message = "is required")
    @Size(min = 6, message = "must be at least 6 characters")
    String password;
}
