package edu.baoss.orderservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Internet speed is absent but Internet Product is selected!")
public class NoInternetSpeedInOrderValueException extends RuntimeException{
    public NoInternetSpeedInOrderValueException(String message) {
        super(message);
    }
}
