package com.example.blogging.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Role {
    public static final String ROLE_PREFIX = "ROLE_";
    private Long id;
    private String name;
    private Role parent;

    public Role name(String name) {
        this.name = ROLE_PREFIX + name.toUpperCase();
        return this;
    }
}
