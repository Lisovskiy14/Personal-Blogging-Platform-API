package com.example.blogging.service.mapper;

import com.example.blogging.domain.Comment;
import com.example.blogging.repository.entity.CommentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentEntityMapper {
    Comment toComment(CommentEntity commentEntity);
}
