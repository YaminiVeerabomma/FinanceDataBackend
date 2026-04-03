package com.example.FinanceDataBackend.entity;





import com.example.FinanceDataBackend.Enum.Role;
import com.example.FinanceDataBackend.Enum.UserStatus;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;
}