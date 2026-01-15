package com.example.blogging.service.exception.notFound;

public class CommentNotFoundException extends ResourceNotFoundException {
    private static final String COMMENT_WITH_ID_NOT_FOUND = "Comment with id %s not found";

    public CommentNotFoundException(String id) {
        super(String.format(COMMENT_WITH_ID_NOT_FOUND, id));
    }
}
