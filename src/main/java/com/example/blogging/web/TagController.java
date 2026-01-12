package com.example.blogging.web;

import com.example.blogging.dto.tag.TagRequestDto;
import com.example.blogging.dto.tag.TagResponseDto;
import com.example.blogging.dto.tag.TagSetResponseDto;
import com.example.blogging.service.TagService;
import com.example.blogging.web.mapper.TagWebMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tags")
public class TagController {
    private final TagService tagService;
    private final TagWebMapper tagWebMapper;

    @GetMapping
    public ResponseEntity<TagSetResponseDto> getAllTags() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new TagSetResponseDto(
                        tagService.getAllTags().stream()
                                .map(tagWebMapper::toResponseDto)
                                .collect(Collectors.toSet())
                ));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<TagResponseDto> getTagBySlug(@PathVariable String slug) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(tagWebMapper.toResponseDto(tagService.getTagBySlug(slug)));
    }

    @PostMapping
    public ResponseEntity<TagResponseDto> createTag(@RequestBody TagRequestDto tagRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(tagWebMapper.toResponseDto(tagService.createTag(tagRequestDto)));
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> deleteTagBySlug(@PathVariable String slug) {
        tagService.deleteTagBySlug(slug);
        return ResponseEntity.noContent().build();
    }
}
