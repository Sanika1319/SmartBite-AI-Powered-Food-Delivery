package com.example.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodId;

    private String name;
    private double price;
    private String description;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private FoodType type;

    @ManyToOne
    private Restaurant restaurant;


}
