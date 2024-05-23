package com.example.Books.exeption;

public class OperationNotPermittedException extends RuntimeException {


    public OperationNotPermittedException(String message) {
        super(message);
    }
}
