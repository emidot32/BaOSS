package edu.baoss.resourceservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="No connected device found for this address.")
public class NoConnectedDeviceFoundException extends RuntimeException {
}
