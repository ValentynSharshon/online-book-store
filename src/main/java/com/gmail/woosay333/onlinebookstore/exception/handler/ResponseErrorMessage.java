package com.gmail.woosay333.onlinebookstore.exception.handler;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public record ResponseErrorMessage(LocalDateTime dateTimeStamp,
                                   HttpStatus httpStatus,
                                   HttpStatusCode httpStatusCode,
                                   String errorMessage) {
}
