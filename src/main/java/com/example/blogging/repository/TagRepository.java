package com.example.blogging.repository;

import com.example.blogging.repository.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {
    Optional<TagEntity> findBySlug(String slug);
    Set<TagEntity> findAllBySlugIn(Set<String> slugs);
    void deleteBySlug(String slug);
}
