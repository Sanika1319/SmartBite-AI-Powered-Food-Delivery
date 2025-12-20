package com.example.repository;

import com.example.entities.Cart;
import com.example.entities.CartItem;
import com.example.entities.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
     Optional<CartItem> findByCartAndFoodItem(Cart cart, FoodItem foodItem);
     void deleteByCart(Cart cart);
}
