package edu.baoss.offerservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="No offers found with this ID.")
public class NoOfferFoundException extends RuntimeException {
}
