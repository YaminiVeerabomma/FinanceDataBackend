package com.example.FinanceDataBackend.entity;

import com.example.FinanceDataBackend.Enum.RecordType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "financial_records")
public class FinancialRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecordType type;   // INCOME or EXPENSE

    private String category;

    @Column(nullable = false)
    private LocalDate date;

    private String notes;

  
}