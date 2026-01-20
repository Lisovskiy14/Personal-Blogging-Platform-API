package com.example.blogging.security.service.impl;

import com.example.blogging.dto.auth.SignInRequestDto;
import com.example.blogging.dto.auth.SignUpRequestDto;
import com.example.blogging.repository.RoleRepository;
import com.example.blogging.repository.UserRepository;
import com.example.blogging.repository.entity.RoleEntity;
import com.example.blogging.repository.entity.UserEntity;
import com.example.blogging.security.service.AuthService;
import com.example.blogging.security.service.JwtService;
import com.example.blogging.service.exception.conflict.impl.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
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
    @Transactional
    public String signUp(SignUpRequestDto signUpRequestDto) {
        if (userRepository.existsByUsername(signUpRequestDto.getUsername())) {
            throw new UserAlreadyExistsException(signUpRequestDto.getUsername());
        }

        RoleEntity roleEntity = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role 'ROLE_USER' is not found."));

        UserEntity userEntity = UserEntity.builder()
                .username(signUpRequestDto.getUsername())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .roles(Set.of(roleEntity))
                .build();

        userEntity = userRepository.saveAndFlush(userEntity);
        return jwtService.generateToken(userEntity);
    }
}
