package com.example.Exception;

public class OrderCannotCancelException extends RuntimeException{
    public OrderCannotCancelException(String message) {
        super(message);
    }
}
