package com.example.blogging.web.mapper;

import com.example.blogging.domain.Comment;
import com.example.blogging.dto.comment.CommentListResponseDto;
import com.example.blogging.dto.comment.CommentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = TagWebMapper.class)
public interface CommentWebMapper {

    default CommentListResponseDto toCommentListResponseDto(List<Comment> comments) {
        if (comments == null || comments.isEmpty()) {
            return null;
        }
        List<CommentResponseDto> commentResponseDtos = comments.stream()
                .map(this::toResponseDto)
                .toList();

        return new CommentListResponseDto(commentResponseDtos);
    }

    @Mapping(target = "postId", source = "post.id")
    CommentResponseDto toResponseDto(Comment comment);


}
