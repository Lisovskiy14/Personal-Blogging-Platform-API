package com.example.blogging.service.exception.notFound.impl;

import com.example.blogging.service.exception.notFound.ResourceNotFoundException;

public class PermissionNotFoundException extends ResourceNotFoundException {
    private static final String PERMISSION_WITH_ID_NOT_FOUND = "Permission with id %s not found";

    public PermissionNotFoundException(Long id) {
        super(String.format(PERMISSION_WITH_ID_NOT_FOUND, id));
    }
}
