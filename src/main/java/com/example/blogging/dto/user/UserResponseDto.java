package com.example.blogging.dto.user;

import com.example.blogging.dto.role.RoleResponseDto;
import lombok.Value;

import java.util.Set;
import java.util.UUID;

@Value
public class UserResponseDto {
    UUID id;
    String username;
    Set<RoleResponseDto> roles;
}
