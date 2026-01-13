package com.example.blogging.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Tag {
    private Long id;
    private String name;
    private String slug;
}
