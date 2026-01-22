package com.example.blogging.service.exception.roleHierarchy.impl;

import com.example.blogging.service.exception.roleHierarchy.IllegalRoleHierarchyException;

public class RootRoleConflictException extends IllegalRoleHierarchyException {
    private static final String ROOT_ROLE_ALREADY_EXISTS = "Root role already exists. To update root role, set childId to %s.";

    public RootRoleConflictException(Long rootRoleId) {
        super(String.format(ROOT_ROLE_ALREADY_EXISTS, rootRoleId));
    }
}
