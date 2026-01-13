package com.example.blogging.service.exception;

public class TagNotFoundException extends ResourceNotFoundException {
    private static final String TAG_WITH_SLUG_NOT_FOUND = "Tag with slug %s not found";

    public TagNotFoundException(String slug) {
        super(String.format(TAG_WITH_SLUG_NOT_FOUND, slug));
    }
}
