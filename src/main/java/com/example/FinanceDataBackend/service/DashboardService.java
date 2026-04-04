package com.example.FinanceDataBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FinanceDataBackend.Enum.RecordType;
import com.example.FinanceDataBackend.entity.FinancialRecord;
import com.example.FinanceDataBackend.repository.FinancialRecordRepository;

@Service
public class DashboardService {

    @Autowired
    private FinancialRecordRepository repo;

    public Double totalIncome() {
        return repo.findByType(RecordType.INCOME)
                .stream().mapToDouble(FinancialRecord::getAmount).sum();
    }

    public Double totalExpense() {
        return repo.findByType(RecordType.EXPENSE)
                .stream().mapToDouble(FinancialRecord::getAmount).sum();
    }

    public Double balance() {
        return totalIncome() - totalExpense();
    }
}