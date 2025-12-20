package com.example.Exception;

public class FoodItemNotFoundException extends RuntimeException{
    public FoodItemNotFoundException(String message) {
        super(message);
    }
}
