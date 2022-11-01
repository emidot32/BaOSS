package edu.baoss.resourceservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Sorry, but we don't have any device yet (")
public class NoDeviceFoundException extends RuntimeException{
}
