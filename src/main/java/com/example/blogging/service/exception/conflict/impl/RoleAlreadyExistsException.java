package com.example.blogging.service.exception.conflict.impl;

import com.example.blogging.service.exception.conflict.ResourceAlreadyExistsException;

public class RoleAlreadyExistsException extends ResourceAlreadyExistsException {
    private static final String ROLE_WITH_NAME_ALREADY_EXISTS = "Role with name %s already exists";

    public RoleAlreadyExistsException(String name) {
        super(String.format(ROLE_WITH_NAME_ALREADY_EXISTS, name));
    }
}
