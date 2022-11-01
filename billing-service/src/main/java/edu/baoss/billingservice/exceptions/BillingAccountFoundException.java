package edu.baoss.billingservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Billing account not found!")
public class BillingAccountFoundException extends RuntimeException{
}
