package com.example.controllers;

import com.example.entities.Cart;
import com.example.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/get/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long userId){
        return new ResponseEntity<>(cartService.getCartByUserId(userId), HttpStatus.OK);
    }

    @PostMapping("/add/{userId}/{foodId}")
    public  ResponseEntity<Cart> addFoodToCart(
            @PathVariable Long userId,
            @PathVariable Long foodId,
            @RequestParam int quantity){
        return new ResponseEntity<>(cartService.addFoodToCart(userId,foodId,quantity),HttpStatus.OK);
    }

    @DeleteMapping("/remove/{userId}/{foodId}")
    public ResponseEntity<Cart> removeFoodFromCart(
            @PathVariable Long userId,
            @PathVariable Long foodId){
        return new ResponseEntity<>(cartService.removeFoodFromCart(userId, foodId),HttpStatus.OK);
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Cart> clearCart(
            @PathVariable Long userId){
        return new ResponseEntity<>(cartService.clearCart(userId),HttpStatus.OK);
    }
}
