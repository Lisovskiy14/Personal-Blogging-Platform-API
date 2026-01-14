package com.example.blogging.web.mapper;

import com.example.blogging.domain.Comment;
import com.example.blogging.dto.comment.CommentResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = TagWebMapper.class)
public interface CommentWebMapper {
    CommentResponseDto toResponseDto(Comment comment);
}
