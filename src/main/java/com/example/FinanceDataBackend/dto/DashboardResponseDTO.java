package com.example.FinanceDataBackend.dto;

import java.time.Month;
import java.util.List;
import java.util.Map;

import com.example.FinanceDataBackend.entity.FinancialRecord;

import lombok.Data;

@Data
public class DashboardResponseDTO {

    private double totalIncome;
    private double totalExpense;
    private double netBalance;

    private Map<String, Double> categoryTotals; // category → amount
    private Map<Month, Double> monthlyTrends; 


    private List<FinancialRecord> recentTransactions;
}