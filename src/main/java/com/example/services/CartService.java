package com.example.services;

import com.example.entities.Cart;

public interface CartService {
    Cart getCartByUserId(Long userId);

    Cart addFoodToCart(Long userId, Long foodId, int quantity);

    Cart removeFoodFromCart(Long userId, Long foodId);

    Cart clearCart(Long userId);

    void recalculateCart(Cart cart);
}
