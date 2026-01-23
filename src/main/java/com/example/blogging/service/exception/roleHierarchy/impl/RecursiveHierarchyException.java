package com.example.blogging.service.exception.roleHierarchy.impl;

import com.example.blogging.service.exception.roleHierarchy.IllegalRoleHierarchyException;

public class RecursiveHierarchyException extends IllegalRoleHierarchyException {
    private static final String RECURSIVE_HIERARCHY = "Recursive hierarchy detected.";

    public RecursiveHierarchyException() {
        super(RECURSIVE_HIERARCHY);
    }
}
