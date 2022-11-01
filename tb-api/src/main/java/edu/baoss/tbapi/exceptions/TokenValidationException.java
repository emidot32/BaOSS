package edu.baoss.tbapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenValidationException extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;
    @Override
    public String getMessage() {
        return message;
    }
}
