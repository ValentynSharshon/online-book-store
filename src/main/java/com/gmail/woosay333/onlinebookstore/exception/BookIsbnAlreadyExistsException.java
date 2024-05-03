package com.gmail.woosay333.onlinebookstore.exception;

public class BookIsbnAlreadyExistsException extends RuntimeException {
    public BookIsbnAlreadyExistsException(String message) {
        super(message);
    }
}
