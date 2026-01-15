package com.example.blogging.web;

import com.example.blogging.dto.user.UserRequestDto;
import com.example.blogging.dto.user.UserResponseDto;
import com.example.blogging.dto.user.UserListResponseDto;
import com.example.blogging.service.UserService;
import com.example.blogging.web.mapper.UserWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Stack;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserWebMapper userWebMapper;

    @GetMapping
    public ResponseEntity<UserListResponseDto> getAllUsers() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new UserListResponseDto(
                        userService.getAllUsers().stream()
                                .map(userWebMapper::toResponseDto)
                                .toList()
                        )
                );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID userId) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userWebMapper.toResponseDto(userService.getUserById(userId)));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userWebMapper.toResponseDto(userService.createUser(userRequestDto)));
    }
}
