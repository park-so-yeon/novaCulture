package com.nova.eunni.auth.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public class LoginException extends RuntimeException {
    private final HttpStatus status;

    public LoginException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
