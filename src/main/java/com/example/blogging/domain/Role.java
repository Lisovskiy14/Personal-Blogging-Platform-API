package com.example.blogging.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class Role {
    public static final String ROLE_PREFIX = "ROLE_";
    private Long id;
    private String name;
    private Set<Permission> permissions;
    private Role parent;
    private Set<Role> children;

    public Role name(String name) {
        this.name = ROLE_PREFIX + name.toUpperCase();
        return this;
    }
}
