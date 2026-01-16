package com.example.blogging.service.impl;

import com.example.blogging.domain.Comment;
import com.example.blogging.dto.comment.CommentRequestDto;
import com.example.blogging.repository.CommentRepository;
import com.example.blogging.repository.PostRepository;
import com.example.blogging.repository.UserRepository;
import com.example.blogging.repository.entity.CommentEntity;
import com.example.blogging.service.CommentService;
import com.example.blogging.service.exception.notFound.CommentNotFoundException;
import com.example.blogging.service.exception.notFound.PostNotFoundException;
import com.example.blogging.service.exception.notFound.UserNotFoundException;
import com.example.blogging.service.mapper.CommentEntityMapper;
import com.example.blogging.service.specification.CommentSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentEntityMapper commentEntityMapper;

    @Override
    @Transactional(readOnly = true)
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
    public Comment getCommentById(UUID id) {
        CommentEntity commentEntity = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id.toString()));
        return commentEntityMapper.toComment(commentEntity);
    }

    @Override
    @Transactional
    public Comment createComment(CommentRequestDto commentRequestDto) {
        if (!userRepository.existsById(commentRequestDto.getAuthorId())) {
            throw new UserNotFoundException(commentRequestDto.getAuthorId().toString());
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
        return commentEntityMapper.toComment(commentEntity);
    }

    @Override
    @Transactional
    public void deleteCommentById(UUID id) {
        commentRepository.deleteById(id);
    }
}
