package edu.baoss.orderservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Total NRC price does not match the selected products!")
public class NrcPriceDiscrepancyException extends RuntimeException{
    public NrcPriceDiscrepancyException(String message) {
        super(message);
    }
}
