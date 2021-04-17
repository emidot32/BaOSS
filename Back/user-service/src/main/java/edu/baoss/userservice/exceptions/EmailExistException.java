package edu.baoss.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="The user with this email exists")
public class EmailExistException extends RuntimeException {
    public EmailExistException(String message) {
        super(message);
    }
}
