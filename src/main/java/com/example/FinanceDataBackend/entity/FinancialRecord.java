package com.example.FinanceDataBackend.entity;



import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class FinancialRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private String type; // INCOME / EXPENSE
    private String category;
    private LocalDate date;
    private String description;

    
}