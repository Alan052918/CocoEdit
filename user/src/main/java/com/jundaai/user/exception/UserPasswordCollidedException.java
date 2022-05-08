package com.jundaai.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserPasswordCollidedException extends RuntimeException {

    public UserPasswordCollidedException(String requestedPassword) {
        super("Requested password " + requestedPassword + " collides with existing passwords.");
    }

}
