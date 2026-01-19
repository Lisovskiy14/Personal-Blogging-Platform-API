package com.example.blogging.service.exception.notFound;

import java.util.UUID;

public class UserNotFoundException extends ResourceNotFoundException {
    private static final String USER_WITH_ID_NOT_FOUND = "User with id %s not found";

    public UserNotFoundException(String id) {
        super(String.format(USER_WITH_ID_NOT_FOUND, id));
    }
}
