package com.example.blogging.web.mapper;

import com.example.blogging.domain.Tag;
import com.example.blogging.dto.tag.TagResponseDto;
import com.example.blogging.dto.tag.TagSetResponseDto;
import org.mapstruct.Mapper;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TagWebMapper {

    default TagSetResponseDto toTagSetResponseDto(Set<Tag> tags) {
        if (tags == null) {
            return null;
        }
        Set<TagResponseDto> tagResponseDtos = tags.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toSet());

        return new TagSetResponseDto(tagResponseDtos);
    }

    TagResponseDto toResponseDto(Tag tag);
}
