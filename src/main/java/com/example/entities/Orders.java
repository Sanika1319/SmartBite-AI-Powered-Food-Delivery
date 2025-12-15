package com.example.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private double totalAmount;

    private String status;
    private String paymentMethod;
    private String createdAt;

    @ManyToOne
    private User user;

    @ManyToOne
    private Address deliveryAddress;
}
