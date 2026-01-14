package com.example.blogging.service.mapper.util;

import com.example.blogging.domain.Tag;
import com.example.blogging.repository.entity.PostTagEntity;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class EntityMapperUtil {

    Set<Tag> postTagEntitiesToTags(Set<PostTagEntity> postTagEntities) {
        return postTagEntities.stream()
                .map(PostTagEntity::getTag)
                .map(tagEntity -> Tag.builder()
                        .id(tagEntity.getId())
                        .name(tagEntity.getName())
                        .slug(tagEntity.getSlug())
                        .build())
                .collect(Collectors.toSet());
    }
}
