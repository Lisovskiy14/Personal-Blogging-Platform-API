package com.example.blogging.service.exception.notFound.impl;

import com.example.blogging.service.exception.notFound.ResourceNotFoundException;

public class RoleNotFoundException extends ResourceNotFoundException {
    private static final String ROLE_WITH_ID_NOT_FOUND = "Role with id %s not found";
    private static final String ROLE_WITH_NAME_NOT_FOUND = "Role with name %s not found";

    public RoleNotFoundException(Long id) {
        super(String.format(ROLE_WITH_ID_NOT_FOUND, id));
    }

    public RoleNotFoundException(String name) {
        super(String.format(ROLE_WITH_NAME_NOT_FOUND, name));
    }
}
