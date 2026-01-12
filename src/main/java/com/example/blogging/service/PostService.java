package com.example.blogging.service;

import com.example.blogging.domain.Post;
import com.example.blogging.dto.post.EditPostRequestDto;
import com.example.blogging.dto.post.PostRequestDto;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<Post> getAllPosts();
    List<Post> getAllPostsByAuthorId(UUID authorId);
    Post getPostById(UUID id);
    Post createPost(PostRequestDto postRequestDto);
    Post editPost(EditPostRequestDto editPostRequestDto, UUID postId);
    void deletePostById(UUID id);
}
