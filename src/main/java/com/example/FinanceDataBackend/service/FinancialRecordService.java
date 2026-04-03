package com.example.FinanceDataBackend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FinanceDataBackend.Enum.RecordType;
import com.example.FinanceDataBackend.dto.FinancialRecordDTO;
import com.example.FinanceDataBackend.entity.FinancialRecord;
import com.example.FinanceDataBackend.repository.FinancialRecordRepository;

import java.time.LocalDate;
import java.util.List;


@Service
public class FinancialRecordService {

    @Autowired
    private FinancialRecordRepository repository;

    public FinancialRecord createRecord(FinancialRecordDTO dto) {
        FinancialRecord record = new FinancialRecord();
        record.setAmount(dto.getAmount());
        record.setType(dto.getType());
        record.setCategory(dto.getCategory());
        record.setDate(dto.getDate());
        record.setNotes(dto.getNotes());
        return repository.save(record);
    }

    public FinancialRecord getRecordById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found with id: " + id));
    }

    public List<FinancialRecord> getAllRecords() {
        return repository.findAll();
    }

    public FinancialRecord updateRecord(Long id, FinancialRecordDTO dto) {
        FinancialRecord record = getRecordById(id);
        record.setAmount(dto.getAmount());
        record.setType(dto.getType());
        record.setCategory(dto.getCategory());
        record.setDate(dto.getDate());
        record.setNotes(dto.getNotes());
        return repository.save(record);
    }

    public void deleteRecord(Long id) {
        FinancialRecord record = getRecordById(id);
        repository.delete(record);
    }

    public List<FinancialRecord> filterRecords(RecordType type, String category,
                                               LocalDate startDate, LocalDate endDate) {

        if (type != null && category != null) return repository.findByTypeAndCategory(type, category);
        if (type != null) return repository.findByType(type);
        if (category != null) return repository.findByCategory(category);
        if (startDate != null && endDate != null) return repository.findByDateBetween(startDate, endDate);

        return repository.findAll();
    }
}