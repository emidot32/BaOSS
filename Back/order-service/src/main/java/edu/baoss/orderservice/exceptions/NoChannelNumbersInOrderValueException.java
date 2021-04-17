package edu.baoss.orderservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Channel numbers is absent but DTV Product is selected!")
public class NoChannelNumbersInOrderValueException extends RuntimeException{
    public NoChannelNumbersInOrderValueException(String message) {
        super(message);
    }
}
