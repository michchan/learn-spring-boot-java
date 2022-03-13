package com.michchan.learnspringjava.restfulwebservices.exception;

import com.michchan.learnspringjava.restfulwebservices.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

// Use this to be shared across multiple controllers
@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionController extends ResponseEntityExceptionHandler {
    private ExceptionResponse getExceptionResponse(Exception ex, WebRequest request) {
        return new ExceptionResponse(
            new Date(),
            ex.getMessage(),
            request.getDescription(false)
        );
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity(this.getExceptionResponse(ex, request), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity(this.getExceptionResponse(ex, request), HttpStatus.NOT_FOUND);
    }
}

