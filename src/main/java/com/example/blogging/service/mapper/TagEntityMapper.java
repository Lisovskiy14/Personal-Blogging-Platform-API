package com.example.blogging.service.mapper;

import com.example.blogging.domain.Tag;
import com.example.blogging.repository.entity.PostTagEntity;
import com.example.blogging.repository.entity.TagEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagEntityMapper {
    Tag toTag(TagEntity tagEntity);
//    PostTagEntity toPostTagEntity(TagEntity tagEntity);
}
