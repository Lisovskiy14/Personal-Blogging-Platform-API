package com.example.blogging.domain;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class Post {
    private UUID id;
    private User author;
    private String title;
    private String content;
    private String imageUrl;
    private Set<Tag> tags;
}
