package com.example.blogging.web.mapper;

import com.example.blogging.domain.Tag;
import com.example.blogging.dto.tag.TagResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagWebMapper {
    TagResponseDto toResponseDto(Tag tag);
}
