package com.example.services;

import com.example.entities.FoodItem;
import com.example.entities.FoodType;

import java.util.List;

public interface FoodItemService {
    FoodItem addFoodItem(Long restaurantId, FoodItem foodItem);
    FoodItem getFoodItemById(Long foodId);
    List<FoodItem> getAllFoodItems();
    List<FoodItem> getFoodItemsByRestaurant(Long restaurantId);
    List<FoodItem> findByType(FoodType type);

    // SEARCH
    List<FoodItem> searchFoodItems(String keyword);

    // UPDATE
    FoodItem updateFoodItem(Long foodId, FoodItem foodItem);

    // DELETE
    void deleteFoodItem(Long foodId);
}
