package com.example.blogging.web;

import com.example.blogging.dto.auth.JwtAuthResponseDto;
import com.example.blogging.dto.auth.SignInRequestDto;
import com.example.blogging.dto.auth.SignUpRequestDto;
import com.example.blogging.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signIn")
    public ResponseEntity<JwtAuthResponseDto> signIn(@Valid @RequestBody SignInRequestDto signInRequestDto) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new JwtAuthResponseDto(authService.signIn(signInRequestDto)));
    }

    @PostMapping("/signUp")
    public ResponseEntity<JwtAuthResponseDto> signIn(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new JwtAuthResponseDto(authService.signUp(signUpRequestDto)));
    }
}
