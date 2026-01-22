package com.example.blogging.security.service.impl;

import com.example.blogging.dto.auth.JwtAuthResponseDto;
import com.example.blogging.dto.auth.SignInRequestDto;
import com.example.blogging.dto.auth.SignUpRequestDto;
import com.example.blogging.dto.auth.SignUpResponseDto;
import com.example.blogging.dto.user.UserResponseDto;
import com.example.blogging.repository.RoleRepository;
import com.example.blogging.repository.UserRepository;
import com.example.blogging.repository.entity.RoleEntity;
import com.example.blogging.repository.entity.UserEntity;
import com.example.blogging.security.service.AuthService;
import com.example.blogging.security.service.JwtService;
import com.example.blogging.service.exception.conflict.impl.UserAlreadyExistsException;
import com.example.blogging.service.mapper.UserEntityMapper;
import com.example.blogging.web.mapper.UserWebMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final UserEntityMapper userEntityMapper;
    private final UserWebMapper userWebMapper;

    @Override
    @Transactional(readOnly = true)
    public String signIn(SignInRequestDto signInRequestDto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                signInRequestDto.getUsername(),
                signInRequestDto.getPassword()
        );

        Authentication authentication = authManager.authenticate(token);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtService.generateToken(userDetails);
    }

    @Override
    @Transactional
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
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
        UserResponseDto userResponseDto = userWebMapper.toResponseDto(
                userEntityMapper.toUser(userEntity));

        JwtAuthResponseDto jwtAuthResponseDto = new JwtAuthResponseDto(
                jwtService.generateToken(userEntity));

        log.info("New User was signed up: {}.", userEntity.getId());
        return new SignUpResponseDto(userResponseDto, jwtAuthResponseDto);
    }
}
