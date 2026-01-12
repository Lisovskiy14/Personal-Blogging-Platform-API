package com.example.blogging.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class Comment {
    private UUID uuid;
    private User author;
    private Post post;
    private String content;
}
