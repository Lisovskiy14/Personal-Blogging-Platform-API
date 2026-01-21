package com.example.blogging.service.exception.notFound.impl;

import com.example.blogging.service.exception.notFound.ResourceNotFoundException;

import java.util.UUID;

public class UserNotFoundException extends ResourceNotFoundException {
    private static final String USER_WITH_ID_NOT_FOUND = "User with id %s not found";
    private static final String USER_WITH_USERNAME_NOT_FOUND = "User with username %s not found";

    public UserNotFoundException(UUID id) {
        super(String.format(USER_WITH_ID_NOT_FOUND, id));
    }

    public UserNotFoundException(String username) {
        super(String.format(USER_WITH_USERNAME_NOT_FOUND, username));
    }
}
