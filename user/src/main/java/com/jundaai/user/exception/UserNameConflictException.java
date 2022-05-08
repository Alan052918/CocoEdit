package com.jundaai.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserNameConflictException extends RuntimeException {

    public UserNameConflictException(String requestedName) {
        super("Requested user name " + requestedName + " conflicts with existing user names.");
    }

}
