package com.example.blogging.dto.role;

import lombok.Value;

import java.util.Set;

@Value
public class RoleResponseDto {
    Long id;
    String name;
    ShortRoleResponseDto parent;
    Set<ShortRoleResponseDto> children;
}
