package com.example.blogging.service.impl;

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
import com.example.blogging.service.exception.notFound.PostNotFoundException;
import com.example.blogging.service.exception.notFound.UserNotFoundException;
import com.example.blogging.service.mapper.PostEntityMapper;
import com.example.blogging.service.specification.PostSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
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
    public List<Post> getAllPosts(UUID authorId, String title) {
        Specification<PostEntity> specification = Specification.allOf(
                PostSpecification.byAuthorId(authorId),
                PostSpecification.byTitle(title)
        );

        List<PostEntity> postEntities = postRepository.findAll(specification);

        return postEntities.stream()
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

        Set<TagEntity> tagEntitySet = tagRepository.findAllByIdIn(postRequestDto.getTagIds());

        Set<PostTagEntity> postTagEntitySet = new HashSet<>();
        for (TagEntity tagEntity : tagEntitySet) {
            PostTagEntity postTagEntity = PostTagEntity.builder()
                    .post(postEntity)
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

        Set<Long> tagIds = editPostRequestDto.getTagIds();
        if (tagIds != null && !tagIds.isEmpty()) {
            if (editPostRequestDto.isToAddTags()) {
                Set<TagEntity> tagEntitySet = tagRepository.findAllByIdIn(tagIds);
                for (TagEntity tagEntity : tagEntitySet) {
                    boolean alreadyExists = postEntity.getTags().stream()
                            .anyMatch(pt -> pt.getTag().getId().equals(tagEntity.getId()));
                    if (!alreadyExists) {
                        postEntity.getTags().add(PostTagEntity.builder()
                                .post(postEntity)
                                .tag(tagEntity)
                                .build());
                    }
                }
            } else {
                postEntity.getTags().removeIf(pt ->
                        tagIds.contains(pt.getTag().getId()));
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
