package com.example.blogging.service.specification;

import com.example.blogging.repository.entity.CommentEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class CommentSpecification {

    public static Specification<CommentEntity> byAuthorId(UUID authorId) {
        if (authorId == null) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("author").get("id"), authorId);
    }

    public static Specification<CommentEntity> byPostId(UUID postId) {
        if (postId == null) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("post").get("id"), postId);
    }
}
