package com.example.Exception;

public class FoodCategoryNotFoundException extends RuntimeException{
    public FoodCategoryNotFoundException(String message) {
        super(message);
    }
}
