package edu.baoss.billingservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Not enough money on the balance for a payment!")
public class NotEnoughMoneyException extends RuntimeException{
}
