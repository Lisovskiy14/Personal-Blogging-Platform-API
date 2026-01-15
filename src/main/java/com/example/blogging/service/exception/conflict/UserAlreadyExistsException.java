package com.example.blogging.service.exception.conflict;

public class UserAlreadyExistsException extends ResourceAlreadyExistsException {
    private static final String USER_WITH_USERNAME_ALREADY_EXISTS = "User with username %s already exists";

    public UserAlreadyExistsException(String username) {
        super(String.format(USER_WITH_USERNAME_ALREADY_EXISTS, username));
    }
}
