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

@Mapper(componentModel = "spring")
public interface PostEntityMapper {
    @Mapping(target = "tags", source = "tags", qualifiedByName = "postTagEntitiesToTags")
    Post toPost(PostEntity postEntity);

    @Named("postTagEntitiesToTags")
    default Set<Tag> postTagEntitiesToTags(Set<PostTagEntity> postTagEntities) {
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
