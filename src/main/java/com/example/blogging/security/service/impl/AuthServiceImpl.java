package com.example.blogging.security.service.impl;

import com.example.blogging.dto.auth.SignInRequestDto;
import com.example.blogging.dto.auth.SignUpRequestDto;
import com.example.blogging.repository.UserRepository;
import com.example.blogging.repository.entity.UserEntity;
import com.example.blogging.security.service.AuthService;
import com.example.blogging.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String signIn(SignInRequestDto signInRequestDto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                signInRequestDto.getUsername(),
                signInRequestDto.getPassword()
        );

        authManager.authenticate(token);

        UserDetails userDetails = userDetailsService.loadUserByUsername(signInRequestDto.getUsername());
        return jwtService.generateToken(userDetails);
    }

    @Override
    public String signUp(SignUpRequestDto signUpRequestDto) {
        UserEntity userEntity = UserEntity.builder()
                .username(signUpRequestDto.getUsername())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .
                .build();
        return "";
    }
}
