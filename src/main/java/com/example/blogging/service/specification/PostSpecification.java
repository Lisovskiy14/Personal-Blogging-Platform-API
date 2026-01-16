package com.example.blogging.service.specification;

import com.example.blogging.repository.entity.PostEntity;
import com.example.blogging.repository.entity.PostTagEntity;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

    public static Specification<PostEntity> byTagIds(Set<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return null;
        }

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Long tagId : tagIds) {
                Subquery<Long> subquery = query.subquery(Long.class);
                Root<PostTagEntity> subRoot = subquery.from(PostTagEntity.class);

                subquery.select(subRoot.get("id"));
                subquery.where(
                        criteriaBuilder.equal(subRoot.get("post"), root),
                        criteriaBuilder.equal(subRoot.get("tag").get("id"), tagId)
                );

                predicates.add(criteriaBuilder.exists(subquery));
            }

            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
