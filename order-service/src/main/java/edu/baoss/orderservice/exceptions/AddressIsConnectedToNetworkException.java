package edu.baoss.orderservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Internet product is already provided for this address!")
public class AddressIsConnectedToNetworkException extends RuntimeException {
    public AddressIsConnectedToNetworkException(String message) {
        super(message);
    }
}
