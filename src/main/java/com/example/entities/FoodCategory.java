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
public class FoodCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String name;
    private String imageUrl;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private List<Restaurant> restaurants = new ArrayList<>();
}
