package edu.baoss.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Invalid data")
public class ValidationException extends RuntimeException{
    public ValidationException(String message) {
        super(message);
    }
}
