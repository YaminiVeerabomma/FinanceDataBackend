package com.example.FinanceDataBackend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FinanceDataBackend.Enum.RecordType;
import com.example.FinanceDataBackend.entity.FinancialRecord;


public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    List<FinancialRecord> findByType(RecordType type);

    List<FinancialRecord> findByCategory(String category);

    List<FinancialRecord> findByDate(LocalDate date);

    List<FinancialRecord> findByTypeAndCategory(RecordType type, String category);

    List<FinancialRecord> findByDateBetween(LocalDate startDate, LocalDate endDate);
}