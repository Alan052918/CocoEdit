package com.jundaai.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserEmailConflictException extends RuntimeException {

    public UserEmailConflictException(String requestedEmail) {
        super("Requested email " + requestedEmail + " conflicts with existing emails.");
    }

}
