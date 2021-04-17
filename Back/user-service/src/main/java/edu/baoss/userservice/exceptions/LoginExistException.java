package edu.baoss.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="The user with this login exists")
public class LoginExistException  extends RuntimeException{
    public LoginExistException(String message) {
        super(message);
    }
}
