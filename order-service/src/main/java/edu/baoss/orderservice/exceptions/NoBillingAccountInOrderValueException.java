package edu.baoss.orderservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Billing Account is not specified!")
public class NoBillingAccountInOrderValueException extends RuntimeException {
    public NoBillingAccountInOrderValueException(String message) {
        super(message);
    }
}
