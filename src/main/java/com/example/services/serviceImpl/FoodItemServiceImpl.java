package com.example.services.serviceImpl;

import com.example.Exception.FoodItemNotFoundException;
import com.example.Exception.RestaurantNotFoundException;
import com.example.entities.FoodItem;
import com.example.entities.FoodType;
import com.example.entities.Restaurant;
import com.example.repository.FoodItemRepository;
import com.example.repository.RestaurantRepository;
import com.example.services.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemServiceImpl implements FoodItemService {
    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Override
    public FoodItem addFoodItem(Long restaurantId, FoodItem foodItem) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));
//        restaurant.setFoodItems();
        foodItem.setRestaurant(restaurant);
        return foodItemRepository.save(foodItem);
    }

    @Override
    public FoodItem getFoodItemById(Long foodId) {
        return foodItemRepository.findById(foodId).orElseThrow(()->new FoodItemNotFoundException("Food Item Not Found"));

    }

    @Override
    public List<FoodItem> getAllFoodItems() {
        return foodItemRepository.findAll();
    }

    @Override
    public List<FoodItem> getFoodItemsByRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));
        return foodItemRepository.findByRestaurant(restaurant);

    }

    @Override
    public List<FoodItem> findByType(FoodType type) {
        return foodItemRepository.getFoodItemsByType(type);
    }

    @Override
    public List<FoodItem> searchFoodItems(String keyword) {
        return foodItemRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    public FoodItem updateFoodItem(Long foodId, FoodItem foodItem) {
        FoodItem foodItem1 = foodItemRepository.findById(foodId).orElseThrow(() -> new FoodItemNotFoundException("Food Item Not Found"));
        foodItem1.setName(foodItem.getName());
        foodItem1.setPrice(foodItem.getPrice());
        foodItem1.setDescription(foodItem.getDescription());
        foodItem1.setImageUrl(foodItem.getImageUrl());
        return foodItemRepository.save(foodItem1);
    }

    @Override
    public void deleteFoodItem(Long foodId) {
        FoodItem foodItem1 = foodItemRepository.findById(foodId).orElseThrow(() -> new FoodItemNotFoundException("Food Item Not Found"));
        foodItemRepository.delete(foodItem1);
    }
}
