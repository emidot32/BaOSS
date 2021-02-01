package edu.baoss.resourceservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ResponseStatus(value=HttpStatus.SERVICE_UNAVAILABLE, reason="Sorry, but we don't have a cable yet (")
public class NoCableFoundException extends RuntimeException{
    private String message;
    @Override
    public String getMessage() {
        return message;
    }
}
