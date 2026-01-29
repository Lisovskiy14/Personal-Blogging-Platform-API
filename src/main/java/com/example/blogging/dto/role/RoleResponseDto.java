package com.example.blogging.dto.role;

import com.example.blogging.dto.permission.PermissionResponseDto;
import lombok.Value;

import java.util.Set;

@Value
public class RoleResponseDto {
    Long id;
    String name;
    Set<PermissionResponseDto> permissions;
    ShortRoleResponseDto parent;
    Set<ShortRoleResponseDto> children;
}
