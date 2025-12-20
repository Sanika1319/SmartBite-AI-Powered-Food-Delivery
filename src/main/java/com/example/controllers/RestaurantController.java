package com.example.controllers;

import com.example.entities.Restaurant;
import com.example.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/add/{categoryId}")
    public ResponseEntity<Restaurant> addRestaurant(@PathVariable Long categoryId, @RequestBody Restaurant restaurant){
        return new ResponseEntity<>(restaurantService.addRestaurant(categoryId,restaurant), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Restaurant>> getAllRestaurant(){
        return new ResponseEntity<>(restaurantService.getAllRestaurants(),HttpStatus.OK);
    }

    @GetMapping("/getByCategory/{categoryId}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByCategory(@PathVariable Long categoryId){
        return new ResponseEntity<>(restaurantService.getRestaurantsByCategory(categoryId),HttpStatus.OK);
    }

    @GetMapping("/searchRestaurants")
    public ResponseEntity<List<Restaurant>> searchRestaurants(@RequestParam String keyword){
        return new ResponseEntity<>(restaurantService.searchRestaurants(keyword),HttpStatus.OK);
    }

    @GetMapping("/getTopRatedRestaurants/{rating}")
    public ResponseEntity<List<Restaurant>> getTopRatedRestaurants(@PathVariable double rating){
        return new ResponseEntity<>(restaurantService.getTopRatedRestaurants(rating),HttpStatus.OK);
    }

    @PutMapping("/updateRestaurant/{restaurantId}/{categoryId}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long restaurantId,@PathVariable Long categoryId, @RequestBody Restaurant restaurant){
        return new ResponseEntity<>(restaurantService.updateRestaurant(restaurantId,categoryId,restaurant),HttpStatus.OK);
    }

    @DeleteMapping("/deleteRestaurant/{restaurantId}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable Long restaurantId){
        restaurantService.deleteRestaurant(restaurantId);
        return  ResponseEntity.ok("Deleted Successfully");
    }
}
