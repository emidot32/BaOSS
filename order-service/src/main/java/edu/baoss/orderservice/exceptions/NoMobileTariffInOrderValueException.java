package edu.baoss.orderservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Mobile tariff is absent but Mobile Product is selected!")
public class NoMobileTariffInOrderValueException extends RuntimeException{
    public NoMobileTariffInOrderValueException(String message) {
        super(message);
    }
}
