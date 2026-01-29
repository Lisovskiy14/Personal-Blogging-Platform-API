package com.example.blogging.service.mapper;

import com.example.blogging.domain.Comment;
import com.example.blogging.repository.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TagEntityMapper.class)
public interface CommentEntityMapper {
    @Mapping(target = "post.comments", ignore = true)
    @Mapping(target = "author.roles", ignore = true)
    Comment toComment(CommentEntity commentEntity);
}
