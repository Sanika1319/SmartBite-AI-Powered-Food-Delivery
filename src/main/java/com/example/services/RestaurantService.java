package com.example.services;

import com.example.entities.Restaurant;

import java.util.List;

public interface RestaurantService {
    Restaurant addRestaurant(Long categoryId, Restaurant restaurant);

    List<Restaurant> getAllRestaurants();
    List<Restaurant> getRestaurantsByCategory(Long categoryId);
    List<Restaurant> searchRestaurants(String keyword);
    List<Restaurant> getTopRatedRestaurants(double rating);

    // UPDATE
    Restaurant updateRestaurant(Long restaurantId,Long categoryId, Restaurant restaurant);

    // DELETE
    void deleteRestaurant(Long restaurantId);
}
