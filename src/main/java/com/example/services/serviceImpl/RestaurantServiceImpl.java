package com.example.services.serviceImpl;

import com.example.Exception.FoodCategoryNotFoundException;
import com.example.Exception.RestaurantNotFoundException;
import com.example.entities.FoodCategory;
import com.example.entities.Restaurant;
import com.example.repository.FoodCategoryRepository;
import com.example.repository.RestaurantRepository;
import com.example.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    @Override
    public Restaurant addRestaurant(Long categoryId,Restaurant restaurant) {
        FoodCategory category = foodCategoryRepository.findById(categoryId).orElseThrow(() -> new FoodCategoryNotFoundException("Category Not Found"));
        restaurant.setCategory(category);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> getRestaurantsByCategory(Long categoryId) {
        FoodCategory category = foodCategoryRepository.findById(categoryId).orElseThrow(() -> new FoodCategoryNotFoundException("Category Not found: " + categoryId));
        return restaurantRepository.findByCategory(category);
    }

    @Override
    public List<Restaurant> searchRestaurants(String keyword) {
        return restaurantRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    public List<Restaurant> getTopRatedRestaurants(double rating) {
        return restaurantRepository.findByRatingGreaterThanEqual(rating);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, Long categoryId, Restaurant restaurant) {
        FoodCategory category = foodCategoryRepository.findById(categoryId).orElseThrow(() -> new FoodCategoryNotFoundException("Category Not found: " + categoryId));
        Restaurant restaurant1 = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + restaurantId));
        restaurant1.setName(restaurant.getName());
        restaurant1.setAddress(restaurant.getAddress());
        restaurant1.setRating(restaurant.getRating());
        restaurant1.setImageUrl(restaurant.getImageUrl());
        restaurant1.setCategory(category);
        return restaurantRepository.save(restaurant1);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException("Restaurant Not Found: " + restaurantId));
        restaurantRepository.delete(restaurant);
    }

}
