package edu.baoss.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="The user with this id card number exists")
public class IdCardNumberExistException extends RuntimeException {
    public IdCardNumberExistException(String message) {
        super(message);
    }
}
