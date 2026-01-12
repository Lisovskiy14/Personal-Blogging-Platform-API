package com.example.blogging.common;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class PostTagID implements Serializable {
    private UUID postId;
    private String tagSlug;
}
