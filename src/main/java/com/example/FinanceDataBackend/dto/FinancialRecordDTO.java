package com.example.FinanceDataBackend.dto;



import java.time.LocalDate;

import com.example.FinanceDataBackend.Enum.RecordType;

import lombok.Data;
@Data
public class FinancialRecordDTO {
    private Double amount;
    private RecordType type;
    private String category;
    private LocalDate date;
    private String notes;
}