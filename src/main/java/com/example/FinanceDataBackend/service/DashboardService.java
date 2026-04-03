package com.example.FinanceDataBackend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FinanceDataBackend.Enum.RecordType;
import com.example.FinanceDataBackend.dto.DashboardResponseDTO;
import com.example.FinanceDataBackend.entity.FinancialRecord;
import com.example.FinanceDataBackend.repository.FinancialRecordRepository;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private FinancialRecordRepository repository;

    public DashboardResponseDTO getDashboardSummary() {

        List<FinancialRecord> records = repository.findAll();

        double totalIncome = records.stream()
                .filter(r -> r.getType() == RecordType.INCOME)
                .mapToDouble(FinancialRecord::getAmount)
                .sum();

        double totalExpense = records.stream()
                .filter(r -> r.getType() == RecordType.EXPENSE)
                .mapToDouble(FinancialRecord::getAmount)
                .sum();

        double netBalance = totalIncome - totalExpense;

        Map<String, Double> categoryTotals = records.stream()
                .collect(Collectors.groupingBy(
                        FinancialRecord::getCategory,
                        Collectors.summingDouble(FinancialRecord::getAmount)
                ));

        Map<Month, Double> monthlyTrends = records.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getDate().getMonth(),
                        Collectors.summingDouble(FinancialRecord::getAmount)
                ));

        List<FinancialRecord> recent = records.stream()
                .sorted(Comparator.comparing(FinancialRecord::getDate).reversed())
                .limit(5)
                .toList();

        DashboardResponseDTO dto = new DashboardResponseDTO();
        dto.setTotalIncome(totalIncome);
        dto.setTotalExpense(totalExpense);
        dto.setNetBalance(netBalance);
        dto.setCategoryTotals(categoryTotals);
        dto.setMonthlyTrends(monthlyTrends);
        dto.setRecentTransactions(recent);

        return dto;
    }
}