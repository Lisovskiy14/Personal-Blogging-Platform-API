package com.example.blogging.service.specification;

import com.example.blogging.repository.entity.PostEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class PostSpecification {

    public static Specification<PostEntity> byAuthorId(UUID authorId) {
        if (authorId == null) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("author").get("id"), authorId);
    }

    public static Specification<PostEntity> byTitle(String title) {
        if (title == null || title.isBlank()) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"
                );
    }
}
