package edu.baoss.orderservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Phone number is absent but Mobile Product is selected!")
public class NoPhoneNumberInOrderValueException extends RuntimeException{
    public NoPhoneNumberInOrderValueException(String message) {
        super(message);
    }
}
