package com.example.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ApiErrors> handleEmailNotFoundException(EmailNotFoundException e){
        ApiErrors errors = new ApiErrors(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(errors,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiErrors> handleInvalidPasswordException(InvalidPasswordException e){
        ApiErrors apiErrors = new ApiErrors(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(apiErrors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<ApiErrors> handleUserIdNotFoundException(UserIdNotFoundException e){
        ApiErrors apiErrors = new ApiErrors(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(apiErrors,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FoodItemNotFoundException.class)
    public ResponseEntity<ApiErrors> handleFoodItemNotFoundException(FoodItemNotFoundException e){
        ApiErrors apiErrors = new ApiErrors(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(apiErrors,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ApiErrors> handleCartNotFoundException(CartNotFoundException e){
        ApiErrors apiErrors = new ApiErrors(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(apiErrors,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ApiErrors> handleRestaurantNotFoundException(RestaurantNotFoundException e){
        ApiErrors apiErrors = new ApiErrors(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(apiErrors,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FoodCategoryNotFoundException.class)
    public ResponseEntity<ApiErrors> handleFoodCategoryNotFoundException(FoodCategoryNotFoundException e){
        ApiErrors apiErrors = new ApiErrors(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(apiErrors,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ApiErrors> handleAddressNotFoundException(AddressNotFoundException e){
        ApiErrors apiErrors = new ApiErrors(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(apiErrors,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiErrors> handleOrderNotFoundException(OrderNotFoundException e){
        ApiErrors apiErrors = new ApiErrors(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(apiErrors,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderCannotCancelException.class)
    public ResponseEntity<ApiErrors> handleOrderCannotCancelException(OrderCannotCancelException e){
        ApiErrors apiErrors = new ApiErrors(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(apiErrors,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AddressException.class)
    public ResponseEntity<ApiErrors> handleAddressException(AddressException e){
        ApiErrors apiErrors = new ApiErrors(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(apiErrors,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(QuantityException.class)
    public ResponseEntity<ApiErrors> handleQuantityException(QuantityException e){
        ApiErrors apiErrors = new ApiErrors(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(apiErrors,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
