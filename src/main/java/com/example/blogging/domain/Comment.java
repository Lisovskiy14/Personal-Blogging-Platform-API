package com.example.blogging.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Comment {
    private UUID id;
    private User author;
    private Post post;
    private String content;
    private LocalDateTime createdAt;
}
