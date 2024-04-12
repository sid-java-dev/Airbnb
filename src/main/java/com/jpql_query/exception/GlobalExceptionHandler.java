package com.jpql_query.exception;

import com.jpql_query.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookingException.class)
    public ResponseEntity<ErrorDetails> handleBookingException(BookingException ex) {
ErrorDetails e=new ErrorDetails(ex.getMessage());
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }
}
