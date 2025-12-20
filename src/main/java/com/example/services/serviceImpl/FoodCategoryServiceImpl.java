package com.example.services.serviceImpl;

import com.example.Exception.FoodCategoryNotFoundException;
import com.example.entities.FoodCategory;
import com.example.repository.FoodCategoryRepository;
import com.example.repository.RestaurantRepository;
import com.example.services.FoodCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodCategoryServiceImpl implements FoodCategoryService {

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public FoodCategory addCategory(FoodCategory category) {
        return foodCategoryRepository.save(category);
    }

    @Override
    public FoodCategory getCategoryById(Long categoryId) {
        return foodCategoryRepository.findById(categoryId).orElseThrow(()-> new FoodCategoryNotFoundException("Category Not found: "+categoryId));
    }

    @Override
    public List<FoodCategory> getAllCategories() {
        return foodCategoryRepository.findAll();
    }

    @Override
    public FoodCategory getCategoryByName(String name) {
        return foodCategoryRepository.findByNameIgnoreCase(name).orElseThrow(()->new FoodCategoryNotFoundException("Food Category not found with name "+name));
    }

    @Override
    public FoodCategory updateCategory(Long categoryId, FoodCategory category) {
        FoodCategory category1 = foodCategoryRepository.findById(categoryId).orElseThrow(() -> new FoodCategoryNotFoundException("Category Not found: " + categoryId));
        category1.setName(category.getName());
        category1.setImageUrl(category.getImageUrl());

        return foodCategoryRepository.save(category1);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        FoodCategory category = foodCategoryRepository.findById(categoryId).orElseThrow(() -> new FoodCategoryNotFoundException("Category Not found: " + categoryId));
        foodCategoryRepository.delete(category);
    }
}
