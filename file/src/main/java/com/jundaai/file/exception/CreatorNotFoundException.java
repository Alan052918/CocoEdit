package com.jundaai.file.exception;

public class CreatorNotFoundException extends RuntimeException {
    public CreatorNotFoundException(Long creatorId) {
        super("File creator by id " + creatorId + " was not found.");
    }

}
