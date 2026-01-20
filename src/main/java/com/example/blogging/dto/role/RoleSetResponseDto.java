package com.example.blogging.dto.role;

import lombok.Value;

import java.util.Set;

@Value
public class RoleSetResponseDto {
    Set<RoleResponseDto> roles;
}
