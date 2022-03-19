package com.michchan.learnspringjava.restfulwebservices.exception;

import com.michchan.learnspringjava.restfulwebservices.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.Locale;

// Use this to be shared across multiple controllers
@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionController extends ResponseEntityExceptionHandler {
    private ExceptionResponse getExceptionResponse(String message, String details, String code) {
        String exDetails = "";
        if (details != null) exDetails = details;
        return new ExceptionResponse(new Date(), message, exDetails, code);
    }

    private FieldErrorProperties getFieldErrorProperties(BindingResult bindingResult) {
        for (Object object : bindingResult.getAllErrors()) {
            if(object instanceof FieldError) {
                FieldError fieldError = (FieldError) object;
                return new FieldErrorProperties(
                    fieldError.getField() + "." + fieldError.getCode().toLowerCase(),
                    fieldError.getDefaultMessage()
                );
            }
        }
        return null;
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity(this.getExceptionResponse(ex.getMessage(), request.getDescription(false), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity(this.getExceptionResponse(ex.getMessage(), request.getDescription(false), null), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        FieldErrorProperties fieldErrorProperties = getFieldErrorProperties(ex.getBindingResult());
        return new ResponseEntity(this.getExceptionResponse(
                "Validation failed",
            fieldErrorProperties.getMessage(),
            fieldErrorProperties.getCode()
        ), HttpStatus.BAD_REQUEST);
    }
}

