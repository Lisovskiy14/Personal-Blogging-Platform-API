package com.example.blogging.dto.role;

import jakarta.validation.constraints.Pattern;
import lombok.Value;

import java.util.Set;

@Value
public class UpdateRoleRequestDto {
    @Pattern(
            regexp = "^[a-zA-Z_]{3,20}$",
            message = "must be between 3 and 20 characters and contain only letters and underscores."
    )
    String name;

    Set<Long> permissionIds;

    Long parentId;
}
