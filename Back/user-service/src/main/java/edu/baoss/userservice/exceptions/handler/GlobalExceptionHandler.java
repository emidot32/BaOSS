package edu.baoss.userservice.exceptions.handler;

import edu.baoss.userservice.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<?> handleUserNotFoundException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(new SimpleException("There is no such user"));
    }

    @Data
    @AllArgsConstructor
    private static class SimpleException {
        private String message;
    }
}
