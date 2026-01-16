package com.example.blogging.service.mapper;

import com.example.blogging.domain.Post;
import com.example.blogging.domain.Tag;
import com.example.blogging.repository.entity.PostEntity;
import com.example.blogging.repository.entity.PostTagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {TagEntityMapper.class, CommentEntityMapper.class})
public interface PostEntityMapper {
    Post toPost(PostEntity postEntity);
}
