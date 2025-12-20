package com.example.repository;

import com.example.entities.FoodCategory;
import com.example.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List <Restaurant> findByCategory(FoodCategory  category);

    List<Restaurant> findByNameContainingIgnoreCase(String keyword);

    List<Restaurant> findByRatingGreaterThanEqual(double rating);

}
