package com.example.blogging.service;

import com.example.blogging.domain.Tag;
import com.example.blogging.dto.tag.TagRequestDto;

import java.util.List;

public interface TagService {
    List<Tag> getAllTags();
    Tag getTagById(Long tagId);
    Tag createTag(TagRequestDto tagRequestDto);
    void deleteTagById(Long tagId);
}
