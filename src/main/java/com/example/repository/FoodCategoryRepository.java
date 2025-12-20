package com.example.repository;

import com.example.entities.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory,Long> {
    Optional<FoodCategory> findByNameIgnoreCase(String name);
}
