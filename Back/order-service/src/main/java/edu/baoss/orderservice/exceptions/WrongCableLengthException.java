package edu.baoss.orderservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Cable length must be between 0 and 30!")
public class WrongCableLengthException extends RuntimeException{
    public WrongCableLengthException(String message) {
        super(message);
    }
}
