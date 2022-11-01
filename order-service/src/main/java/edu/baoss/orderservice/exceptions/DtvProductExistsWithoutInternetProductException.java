package edu.baoss.orderservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="DTV Product is selected but Internet Product is not selected!")
public class DtvProductExistsWithoutInternetProductException extends RuntimeException{
    public DtvProductExistsWithoutInternetProductException(String message) {
        super(message);
    }
}
