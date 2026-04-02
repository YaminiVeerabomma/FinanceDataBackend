package com.example.FinanceDataBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FinanceDataBackend.entity.FinancialRecord;


public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

}
