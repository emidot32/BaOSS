package edu.baoss.orderservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Your building is not connected to our network.\nWe will notify you when it is done.")
public class HomeIsNotConnectedToNetworkException extends RuntimeException {
    public HomeIsNotConnectedToNetworkException(String message) {
        super(message);
    }
}
