package com.example.blogging.service.impl;

import com.example.blogging.common.PostTagId;
import com.example.blogging.domain.Post;
import com.example.blogging.dto.post.EditPostRequestDto;
import com.example.blogging.dto.post.PostRequestDto;
import com.example.blogging.repository.PostRepository;
import com.example.blogging.repository.TagRepository;
import com.example.blogging.repository.UserRepository;
import com.example.blogging.repository.entity.PostEntity;
import com.example.blogging.repository.entity.PostTagEntity;
import com.example.blogging.repository.entity.TagEntity;
import com.example.blogging.service.PostService;
import com.example.blogging.service.exception.PostNotFoundException;
import com.example.blogging.service.exception.UserNotFoundException;
import com.example.blogging.service.mapper.PostEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final PostRepository postRepository;
    private final PostEntityMapper postEntityMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAll().stream()
                .map(postEntityMapper::toPost)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPostsByAuthorId(UUID authorId) {
        return postRepository.findAllByAuthorId(authorId).stream()
                .map(postEntityMapper::toPost)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Post getPostById(UUID id) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postEntityMapper.toPost(postEntity);
    }

    @Override
    @Transactional
    public Post createPost(PostRequestDto postRequestDto) {
        PostEntity postEntity = PostEntity.builder()
                .author(userRepository.getReferenceById(postRequestDto.getAuthorId()))
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .imageUrl(postRequestDto.getImageUrl())
                .build();

        Set<TagEntity> tagEntitySet = tagRepository.findAllBySlugIn(postRequestDto.getTagSlugs());

        Set<PostTagEntity> postTagEntitySet = new HashSet<>();
        for (TagEntity tagEntity : tagEntitySet) {
            PostTagId postTagId = new PostTagId(postEntity.getId(), tagEntity.getId());
            PostTagEntity postTagEntity = PostTagEntity.builder()
                    .id(postTagId)
                    .tag(tagEntity)
                    .build();
            postTagEntitySet.add(postTagEntity);
        }

        postEntity.setTags(postTagEntitySet);

        try {
            postEntity = postRepository.save(postEntity);
        } catch (DataIntegrityViolationException e) {
            throw new UserNotFoundException(postRequestDto.getAuthorId().toString());
        }

        return postEntityMapper.toPost(postEntity);
    }

    @Override
    @Transactional
    public Post editPost(EditPostRequestDto editPostRequestDto, UUID postId) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));

        if (editPostRequestDto.getTitle() != null) {
            postEntity.setTitle(editPostRequestDto.getTitle());
        }
        if (editPostRequestDto.getContent() != null) {
            postEntity.setContent(editPostRequestDto.getContent());
        }
        if (editPostRequestDto.getImageUrl() != null) {
            postEntity.setImageUrl(editPostRequestDto.getImageUrl());
        }
        Set<String> tagSlugs = editPostRequestDto.getTagSlugs();
        if (tagSlugs != null && !tagSlugs.isEmpty()) {
            Set<TagEntity> tagEntitySet = tagRepository.findAllBySlugIn(tagSlugs);
            if (editPostRequestDto.isToAddTags()) {
                for (TagEntity tagEntity : tagEntitySet) {
                    postEntity.getTags().add(PostTagEntity.builder()
                            .id(new PostTagId(postId, tagEntity.getId()))
                            .build());
                }
            } else {
                postEntity.getTags().stream()
                        .filter(postTagEntity -> tagEntitySet
                                .contains(postTagEntity.getTag()))
                        .forEach(postEntity.getTags()::remove);
            }
        }

        postEntity = postRepository.save(postEntity);
        return postEntityMapper.toPost(postEntity);
    }

    @Override
    @Transactional
    public void deletePostById(UUID id) {
        postRepository.deleteById(id);
    }
}
