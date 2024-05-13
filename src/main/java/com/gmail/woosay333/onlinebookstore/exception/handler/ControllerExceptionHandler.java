package com.gmail.woosay333.onlinebookstore.exception.handler;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.gmail.woosay333.onlinebookstore.exception.BookIsbnAlreadyExistsException;
import com.gmail.woosay333.onlinebookstore.exception.DataProcessingException;
import com.gmail.woosay333.onlinebookstore.exception.EntityNotFoundException;
import com.gmail.woosay333.onlinebookstore.exception.RegistrationException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
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
                .map(this::getErrorMessage)
                .toList();
        return getResponseEntity(HttpStatus.valueOf(status.value()), errors);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        return getResponseEntity(HttpStatus.valueOf(status.value()), ex.getLocalizedMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex) {
        return getResponseEntity(FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
        return getResponseEntity(UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return getResponseEntity(NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(EntityNotFoundException ex) {
        return getResponseEntity(NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DataProcessingException.class)
    protected ResponseEntity<Object> handleDataProcessingException(DataProcessingException ex) {
        return getResponseEntity(INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler({BookIsbnAlreadyExistsException.class, RegistrationException.class})
    protected ResponseEntity<Object> handleBookIsbnAlreadyExistsException(Exception ex) {
        return getResponseEntity(CONFLICT, ex.getMessage());
    }

    private String getErrorMessage(ObjectError error) {
        return error.getDefaultMessage();
    }

    private ResponseEntity<Object> getResponseEntity(HttpStatus status, Object error) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        Map<String, Object> detail = new LinkedHashMap<>();
        detail.put("error", error);
        detail.put("timestamp", LocalDateTime.now().toString());
        problemDetail.setProperties(detail);
        return ResponseEntity.of(problemDetail).build();
    }
}
