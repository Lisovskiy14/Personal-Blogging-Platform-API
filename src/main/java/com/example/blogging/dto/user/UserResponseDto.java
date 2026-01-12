package com.example.blogging.dto.user;

import lombok.Value;

import java.util.UUID;

@Value
public class UserResponseDto {
    UUID id;
    String username;
}
