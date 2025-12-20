package com.example.controllers;

import com.example.entities.FoodCategory;
import com.example.services.FoodCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class FoodCategoryController {
   @Autowired
   private FoodCategoryService foodCategoryService;
   @PostMapping("/add")
   public ResponseEntity<FoodCategory> addCategory(@RequestBody FoodCategory category){
        return new ResponseEntity<>(foodCategoryService.addCategory(category), HttpStatus.CREATED);
    }

    @GetMapping("/getById/{categoryId}")
    public ResponseEntity<FoodCategory>getCategoryById(@PathVariable Long categoryId){
       return new ResponseEntity<>(foodCategoryService.getCategoryById(categoryId),HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<FoodCategory>> getAllCategories(){
       return new ResponseEntity<>(foodCategoryService.getAllCategories(),HttpStatus.OK);

    }

    @PostMapping("/getCategoryByName")
    public ResponseEntity<FoodCategory> getCategoryByName(@RequestParam String name){
       return new ResponseEntity<>(foodCategoryService.getCategoryByName(name),HttpStatus.OK);
    }

    @PutMapping("/updateCategory/{categoryId}")
    public ResponseEntity<FoodCategory> updateCategory(@PathVariable Long categoryId, @RequestBody FoodCategory category){
       return new ResponseEntity<>(foodCategoryService.updateCategory(categoryId,category),HttpStatus.OK);
    }

    @DeleteMapping("/deleteCategory/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId){
       foodCategoryService.deleteCategory(categoryId);
       return ResponseEntity.ok().body("Food Category Deleted Successfully");
    }
}
