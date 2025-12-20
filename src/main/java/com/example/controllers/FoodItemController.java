package com.example.controllers;

import com.example.entities.FoodItem;
import com.example.entities.FoodType;
import com.example.services.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class FoodItemController {
    @Autowired
    private FoodItemService foodItemService;
    @PostMapping("/add/{restaurantId}")
    public ResponseEntity<FoodItem> addFoodItem(
            @PathVariable Long restaurantId,
            @RequestBody FoodItem foodItem){
        return  new ResponseEntity<>(foodItemService.addFoodItem(restaurantId, foodItem), HttpStatus.CREATED);
    }

    @GetMapping("/getById/{foodId}")
    public ResponseEntity<FoodItem>  getFoodItemById(@PathVariable Long foodId){
        return new ResponseEntity<>(foodItemService.getFoodItemById(foodId),HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<FoodItem>> getAllFoodItems(){
        return new ResponseEntity<>(foodItemService.getAllFoodItems(),HttpStatus.OK);
    }

    @GetMapping("/getByRestaurant/{restaurantId}")
    public ResponseEntity<List<FoodItem>> getFoodItemsByRestaurant(@PathVariable Long restaurantId){
        return new ResponseEntity<>(foodItemService.getFoodItemsByRestaurant(restaurantId),HttpStatus.OK);
    }

    @GetMapping("/findByType")
    public ResponseEntity<List<FoodItem>> findByType(@RequestParam String type){
        FoodType foodType = FoodType.valueOf(type.toUpperCase());
        return new ResponseEntity<>(foodItemService.findByType(foodType),HttpStatus.OK);
    }

    @GetMapping("/searchFoodItems")
    public ResponseEntity<List<FoodItem>> searchFoodItems(@RequestParam String keyword){
        return new ResponseEntity<>(foodItemService.searchFoodItems(keyword),HttpStatus.OK);
    }

    @PutMapping("/updateFood/{foodId}")
    public ResponseEntity<FoodItem> updateFoodItem(@PathVariable Long foodId,@RequestBody FoodItem foodItem){
        return new ResponseEntity<>(foodItemService.updateFoodItem(foodId, foodItem),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{foodId}")
    public ResponseEntity<?> deleteFoodItem(@PathVariable Long foodId){
        foodItemService.deleteFoodItem(foodId);
        return ResponseEntity.ok("Deleted Successfully");
    }


}
