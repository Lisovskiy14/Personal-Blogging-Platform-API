package com.example.blogging.service.impl;

import com.example.blogging.domain.Tag;
import com.example.blogging.dto.tag.TagRequestDto;
import com.example.blogging.repository.TagRepository;
import com.example.blogging.repository.entity.TagEntity;
import com.example.blogging.service.TagService;
import com.example.blogging.service.exception.notFound.impl.TagNotFoundException;
import com.example.blogging.service.mapper.TagEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagEntityMapper tagEntityMapper;

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('tag:read')")
    public List<Tag> getAllTags() {
        return tagRepository.findAll().stream()
                .map(tagEntityMapper::toTag)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('tag:read')")
    public Tag getTagById(Long tagId) {
        TagEntity tagEntity = tagRepository.findById(tagId)
                .orElseThrow(() -> new TagNotFoundException(tagId));
        return tagEntityMapper.toTag(tagEntity);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('tag:write')")
    public Tag createTag(TagRequestDto tagRequestDto) {
        TagEntity tagEntity = TagEntity.builder()
                .name(tagRequestDto.getName())
                .slug(tagRequestDto.getSlug())
                .build();

        tagEntity = tagRepository.save(tagEntity);
        log.info("New Tag was created: {}.", tagEntity.getId());
        return tagEntityMapper.toTag(tagEntity);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('tag:delete')")
    public void deleteTagById(Long tagId) {
        tagRepository.deleteById(tagId);
        log.info("Tag with id {} was deleted.", tagId);
    }
}
