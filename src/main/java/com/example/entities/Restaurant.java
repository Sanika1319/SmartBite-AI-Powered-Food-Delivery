package com.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

    private String name;
    private String address;
    private double rating;
    private String imageUrl;

    @ManyToOne
    private FoodCategory category;

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private List<FoodItem> foodItems = new ArrayList<>();
}
