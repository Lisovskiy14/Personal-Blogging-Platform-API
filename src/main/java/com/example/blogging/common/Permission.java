package com.example.blogging.common;

public enum Permission {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete"),
    ROLE_READ("role:read"),
    ROLE_WRITE("role:write"),
    ROLE_UPDATE("role:update"),
    ROLE_DELETE("role:delete"),
    PERMISSION_READ("permission:read"),
    PERMISSION_WRITE("permission:write"),
    PERMISSION_DELETE("permission:delete"),
    POST_READ("post:read"),
    POST_WRITE("post:write"),
    POST_UPDATE("post:update"),
    POST_DELETE("post:delete"),
    COMMENT_READ("comment:read"),
    COMMENT_WRITE("comment:write"),
    COMMENT_DELETE("comment:delete"),
    TAG_READ("tag:read"),
    TAG_WRITE("tag:write"),
    TAG_DELETE("tag:delete");

    private String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getValue() {
        return permission;
    }
}
