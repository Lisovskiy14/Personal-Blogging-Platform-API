package com.example.blogging.service;

import com.example.blogging.domain.User;
import com.example.blogging.dto.user.UpdateUserRolesRequestDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(UUID id);
    User updateUserRoles(UUID userId, UpdateUserRolesRequestDto updateUserRolesRequestDto);
}
