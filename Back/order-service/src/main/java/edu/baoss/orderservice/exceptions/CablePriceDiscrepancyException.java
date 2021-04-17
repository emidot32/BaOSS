package edu.baoss.orderservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Cable price is calculated incorrectly!")
public class CablePriceDiscrepancyException extends RuntimeException {
    public CablePriceDiscrepancyException(String message) {
        super(message);
    }
}
