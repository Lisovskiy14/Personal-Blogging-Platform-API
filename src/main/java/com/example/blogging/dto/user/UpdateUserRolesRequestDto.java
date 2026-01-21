package com.example.blogging.dto.user;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.Set;

@Value
public class UpdateUserRolesRequestDto {
    @Size(min = 1)
    Set<Long> roleIds;
}
