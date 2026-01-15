package com.example.blogging.common;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class PostTagId implements Serializable {
    @JoinColumn(name = "post_id")
    private UUID postId;

    @JoinColumn(name = "tag_id")
    private Long tagId;
}
