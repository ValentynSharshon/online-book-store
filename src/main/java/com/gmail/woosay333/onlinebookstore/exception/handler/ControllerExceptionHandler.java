package com.gmail.woosay333.onlinebookstore.exception.handler;

import com.gmail.woosay333.onlinebookstore.exception.BookIsbnAlreadyExistsException;
import com.gmail.woosay333.onlinebookstore.exception.DataProcessingException;
import com.gmail.woosay333.onlinebookstore.exception.EntityNotFoundException;
import com.gmail.woosay333.onlinebookstore.exception.RegistrationException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        ResponseErrorMessage responseErrorMessage = new ResponseErrorMessage(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                HttpStatusCode.valueOf(400),
                errors
        );
        return new ResponseEntity<>(responseErrorMessage, headers, status);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFoundException(Exception ex) {
        ResponseErrorMessage responseErrorMessage = new ResponseErrorMessage(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                HttpStatusCode.valueOf(400),
                List.of(ex.getMessage())
        );
        return new ResponseEntity<>(responseErrorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DataProcessingException.class})
    protected ResponseEntity<Object> handleDataProcessingException(Exception ex) {
        ResponseErrorMessage responseErrorMessage = new ResponseErrorMessage(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatusCode.valueOf(500),
                List.of(ex.getMessage())
        );
        return new ResponseEntity<>(responseErrorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({BookIsbnAlreadyExistsException.class, RegistrationException.class})
    protected ResponseEntity<Object> handleBookIsbnAlreadyExistsException(Exception ex) {
        ResponseErrorMessage responseErrorMessage = new ResponseErrorMessage(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                HttpStatusCode.valueOf(400),
                List.of(ex.getMessage())
        );
        return new ResponseEntity<>(responseErrorMessage, HttpStatus.BAD_REQUEST);
    }
}
