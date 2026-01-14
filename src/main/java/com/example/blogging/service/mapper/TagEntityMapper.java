package com.example.blogging.service.mapper;

import com.example.blogging.domain.Tag;
import com.example.blogging.repository.entity.PostTagEntity;
import com.example.blogging.repository.entity.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TagEntityMapper {
    Tag toTag(TagEntity tagEntity);

    @Mapping(target = "id", source = "tag.id")
    @Mapping(target = "name", source = "tag.name")
    @Mapping(target = "slug", source = "tag.slug")
    Tag postTagEntityToTag(PostTagEntity postTagEntity);
}
