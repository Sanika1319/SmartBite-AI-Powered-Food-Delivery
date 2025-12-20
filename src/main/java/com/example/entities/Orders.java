package com.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
//    private String paymentMethod;
    private LocalDateTime  createdAt;
    @Column(nullable = false)
    private LocalDateTime statusUpdatedAt;


    @ManyToOne
    private User user;

    @ManyToOne

    private Address deliveryAddress;
}
