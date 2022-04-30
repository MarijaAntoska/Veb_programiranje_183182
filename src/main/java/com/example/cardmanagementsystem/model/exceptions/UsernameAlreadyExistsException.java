package com.example.cardmanagementsystem.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String message) {
       super (String.format("User with username: %s already exists", message));
    }

    public UsernameAlreadyExistsException() {
    }
}
