package com.example.blogging.dto.auth;

import com.example.blogging.dto.user.UserResponseDto;
import lombok.Value;

@Value
public class SignUpResponseDto {
    UserResponseDto user;
    JwtAuthResponseDto token;
}
