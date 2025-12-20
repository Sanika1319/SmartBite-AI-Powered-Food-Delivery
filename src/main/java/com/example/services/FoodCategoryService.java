package com.example.services;

import com.example.entities.FoodCategory;

import java.util.List;

public interface FoodCategoryService {
    FoodCategory addCategory(FoodCategory category);
    FoodCategory getCategoryById(Long categoryId);

    List<FoodCategory> getAllCategories();
    FoodCategory getCategoryByName(String name);

    // UPDATE
    FoodCategory updateCategory(Long categoryId, FoodCategory category);

    // DELETE
    void deleteCategory(Long categoryId);
}
