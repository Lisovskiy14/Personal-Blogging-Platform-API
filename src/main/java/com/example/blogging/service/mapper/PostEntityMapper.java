package com.example.blogging.service.mapper;

import com.example.blogging.domain.Post;
import com.example.blogging.repository.entity.PostEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TagEntityMapper.class, CommentEntityMapper.class})
public interface PostEntityMapper {
    Post toPost(PostEntity postEntity);
}
