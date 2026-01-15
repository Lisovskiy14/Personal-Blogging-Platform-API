package com.example.blogging.web.mapper;

import com.example.blogging.domain.Comment;
import com.example.blogging.dto.comment.CommentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TagWebMapper.class)
public interface CommentWebMapper {
    @Mapping(target = "postId", source = "post.id")
    CommentResponseDto toResponseDto(Comment comment);
}
