package com.example.FinanceDataBackend.dto;



import java.time.LocalDate;

import com.example.FinanceDataBackend.Enum.RecordType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor   
public class FinancialRecordDTO {
    private Double amount;
    private RecordType type;
    private String category;
    private LocalDate date;
    private String notes;
}