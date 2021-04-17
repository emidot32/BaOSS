package edu.baoss.orderservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Order configuration needs address for delivery or installation!")
public class NoAddressInOrderValueException extends RuntimeException {
    public NoAddressInOrderValueException(String message) {
        super(message);
    }
}
