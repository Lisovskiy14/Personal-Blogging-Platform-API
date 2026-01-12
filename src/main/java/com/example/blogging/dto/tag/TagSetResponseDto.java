package com.example.blogging.dto.tag;

import lombok.Value;

import java.util.Set;

@Value
public class TagSetResponseDto {
    Set<TagResponseDto> tags;
}
