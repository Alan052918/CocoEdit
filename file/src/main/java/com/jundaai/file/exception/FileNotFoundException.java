package com.jundaai.file.exception;

public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException(Long fileId) {
        super("File by id " + fileId + " was not found.");
    }

}
