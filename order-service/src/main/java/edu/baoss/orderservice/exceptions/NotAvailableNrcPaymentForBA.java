package edu.baoss.orderservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="You don't have enough money on selected billing account for NRC payment !")
public class NotAvailableNrcPaymentForBA extends RuntimeException {
    public NotAvailableNrcPaymentForBA(String message) {
        super(message);
    }
}
