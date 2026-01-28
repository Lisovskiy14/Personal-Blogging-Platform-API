package com.example.blogging.dto.permission;

import lombok.Value;

import java.util.Set;

@Value
public class PermissionSetResponseDto {
    Set<PermissionResponseDto> permissions;
}
