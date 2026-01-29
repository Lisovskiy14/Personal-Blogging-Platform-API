package com.example.blogging.service.impl;

import com.example.blogging.domain.Comment;
import com.example.blogging.dto.comment.CommentRequestDto;
import com.example.blogging.repository.CommentRepository;
import com.example.blogging.repository.PostRepository;
import com.example.blogging.repository.UserRepository;
import com.example.blogging.repository.entity.CommentEntity;
import com.example.blogging.service.CommentService;
import com.example.blogging.service.exception.notFound.impl.CommentNotFoundException;
import com.example.blogging.service.exception.notFound.impl.PostNotFoundException;
import com.example.blogging.service.exception.notFound.impl.UserNotFoundException;
import com.example.blogging.service.mapper.CommentEntityMapper;
import com.example.blogging.service.specification.CommentSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentEntityMapper commentEntityMapper;

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('comment:read')")
    public List<Comment> getAllComments(UUID postId, UUID authorId) {
        Specification<CommentEntity> specification = Specification.allOf(
                CommentSpecification.byAuthorId(authorId),
                CommentSpecification.byPostId(postId)
        );

        List<CommentEntity> commentEntities = commentRepository.findAll(specification);
        return commentEntities.stream()
                .map(commentEntityMapper::toComment)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('comment:read')")
    public Comment getCommentById(UUID id) {
        CommentEntity commentEntity = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id.toString()));
        return commentEntityMapper.toComment(commentEntity);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('comment:write')")
    public Comment createComment(CommentRequestDto commentRequestDto) {
        if (!userRepository.existsById(commentRequestDto.getAuthorId())) {
            throw new UserNotFoundException(commentRequestDto.getAuthorId());
        }
        if (!postRepository.existsById(commentRequestDto.getPostId())) {
            throw new PostNotFoundException(commentRequestDto.getPostId().toString());
        }

        CommentEntity commentEntity = CommentEntity.builder()
                .author(userRepository.getReferenceById(commentRequestDto.getAuthorId()))
                .post(postRepository.getReferenceById(commentRequestDto.getPostId()))
                .content(commentRequestDto.getContent())
                .build();

        commentEntity = commentRepository.saveAndFlush(commentEntity);
        log.info("New Comment was created: {}.", commentEntity.getId());
        return commentEntityMapper.toComment(commentEntity);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('comment:delete')")
    public void deleteCommentById(UUID id) {
        commentRepository.deleteById(id);
        log.info("Comment with id {} was deleted.", id);
    }
}
