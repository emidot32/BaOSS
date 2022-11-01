package edu.baoss.resourceservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Sorry, but we don't have any SIM card yet (")
public class NoPhoneNumberFoundException extends RuntimeException {
    public NoPhoneNumberFoundException(String message) {
        super(message);
    }

    public NoPhoneNumberFoundException() {
    }
}
