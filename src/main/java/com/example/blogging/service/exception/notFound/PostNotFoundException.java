package com.example.blogging.service.exception.notFound;

public class PostNotFoundException extends ResourceNotFoundException {
    private static final String POST_WITH_ID_NOT_FOUND = "Post with id %s not found";

    public PostNotFoundException(String id) {
        super(String.format(POST_WITH_ID_NOT_FOUND, id));
    }
}
