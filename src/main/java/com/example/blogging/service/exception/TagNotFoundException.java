package com.example.blogging.service.exception;

public class TagNotFoundException extends ResourceNotFoundException {
    private static final String TAG_WITH_SLUG_NOT_FOUND = "Tag with id %s not found";

    public TagNotFoundException(Long id) {
        super(String.format(TAG_WITH_SLUG_NOT_FOUND, id));
    }
}
