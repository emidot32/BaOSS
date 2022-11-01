package edu.baoss.resourceservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value=HttpStatus.SERVICE_UNAVAILABLE, reason="Sorry, but we don't have a cables.json yet (")
public class NoCableFoundException extends RuntimeException{

}
