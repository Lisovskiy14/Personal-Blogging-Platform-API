package com.example.blogging.security.service;

import com.example.blogging.domain.User;
import com.example.blogging.dto.auth.SignInRequestDto;
import com.example.blogging.dto.auth.SignUpRequestDto;
import com.example.blogging.dto.auth.SignUpResponseDto;

public interface AuthService {
    String signIn(SignInRequestDto signInRequestDto);
    SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto);
}
