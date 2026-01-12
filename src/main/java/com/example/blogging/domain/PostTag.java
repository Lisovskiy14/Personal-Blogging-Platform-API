package com.example.blogging.domain;

import lombok.Data;

@Data
public class PostTag {
    private Post post;
    private Tag tag;
}
