package com.example.blogging.service.impl;

import com.example.blogging.domain.Tag;
import com.example.blogging.dto.tag.TagRequestDto;
import com.example.blogging.repository.TagRepository;
import com.example.blogging.repository.entity.TagEntity;
import com.example.blogging.service.TagService;
import com.example.blogging.service.exception.notFound.TagNotFoundException;
import com.example.blogging.service.mapper.TagEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagEntityMapper tagEntityMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Tag> getAllTags() {
        return tagRepository.findAll().stream()
                .map(tagEntityMapper::toTag)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Tag getTagById(Long tagId) {
        TagEntity tagEntity = tagRepository.findById(tagId)
                .orElseThrow(() -> new TagNotFoundException(tagId));
        return tagEntityMapper.toTag(tagEntity);
    }

    @Override
    @Transactional
    public Tag createTag(TagRequestDto tagRequestDto) {
        TagEntity tagEntity = TagEntity.builder()
                .name(tagRequestDto.getName())
                .slug(tagRequestDto.getSlug())
                .build();

        tagEntity = tagRepository.save(tagEntity);
        return tagEntityMapper.toTag(tagEntity);
    }

    @Override
    @Transactional
    public void deleteTagById(Long tagId) {
        tagRepository.deleteById(tagId);
    }
}
