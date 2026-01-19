package com.example.blogging.security.service;

import com.example.blogging.dto.auth.SignInRequestDto;
import com.example.blogging.dto.auth.SignUpRequestDto;

public interface AuthService {
    String signIn(SignInRequestDto signInRequestDto);
    String signUp(SignUpRequestDto signUpRequestDto);
}
