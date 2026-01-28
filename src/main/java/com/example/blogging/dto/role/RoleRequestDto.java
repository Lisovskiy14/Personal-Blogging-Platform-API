package com.example.blogging.dto.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.Set;

@Value
public class RoleRequestDto {
    @NotBlank(message = "is required")
    @Pattern(
            regexp = "^[a-zA-Z_]{3,20}$",
            message = "must be between 3 and 20 characters and contain only letters and underscores."
    )
    String name;

    @NotNull(message = "is required")
    @Size(min = 1, message = "must contain at least one permission")
    Set<Long> permissionIds;

    Long parentId;

    Long childId;
}
