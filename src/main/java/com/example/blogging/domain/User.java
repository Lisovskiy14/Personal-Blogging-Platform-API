package com.example.blogging.domain;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class User {
    private UUID id;
    private String username;
    private Set<Role> roles;
}
