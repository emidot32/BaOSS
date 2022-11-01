package edu.baoss.orderservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Total MRC price does not match the selected products!")
public class MrcPriceDiscrepancyException extends RuntimeException {
    public MrcPriceDiscrepancyException(String message) {
        super(message);
    }
}
