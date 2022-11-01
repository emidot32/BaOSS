package edu.baoss.orderservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Delivery date or time is not specified!")
public class DeliveryNotSpecifiedException extends RuntimeException{
    public DeliveryNotSpecifiedException(String message) {
        super(message);
    }
}
