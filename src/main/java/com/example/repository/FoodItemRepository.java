package com.example.repository;

import com.example.entities.FoodItem;
import com.example.entities.FoodType;
import com.example.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem,Long> {
    List<FoodItem> findByRestaurant(Restaurant restaurant);
    List<FoodItem> findByNameContainingIgnoreCase(String name);
    List<FoodItem> getFoodItemsByType(FoodType type);
}
