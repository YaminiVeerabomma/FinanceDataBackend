package com.example.FinanceDataBackend.entity;



import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String role; // ADMIN, ANALYST, VIEWER
    private boolean active;

    
}