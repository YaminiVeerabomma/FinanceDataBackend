package com.example.FinanceDataBackend.entity;



import jakarta.persistence.*;

import lombok.*;


import java.time.LocalDate;

import com.example.FinanceDataBackend.Enum.RecordType;



@Entity
@Data
@Table(name = "financial_records")
public class FinancialRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private RecordType type; // INCOME / EXPENSE

    private String category;

    private LocalDate date;

    private String notes;
}