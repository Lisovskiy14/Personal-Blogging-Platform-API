package com.example.blogging.dto.tag;

import lombok.Value;

@Value
public class TagResponseDto {
    Long id;
    String name;
    String slug;
}
