package com.example.blogging.web.mapper;

import com.example.blogging.domain.Post;
import com.example.blogging.dto.post.PostResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostWebMapper {
    PostResponseDto toResponseDto(Post post);
}
