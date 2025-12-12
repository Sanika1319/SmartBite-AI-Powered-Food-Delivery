package com.example.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private  String name;
    private String email;
    private  String password;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;
}
