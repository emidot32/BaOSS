package edu.baoss.userservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="The user with this login exists")
public class LoginExistException  extends RuntimeException{
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }
}
