package com.example.FinanceDataBackend.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.FinanceDataBackend.Enum.RecordType;
import com.example.FinanceDataBackend.dto.FinancialRecordDTO;
import com.example.FinanceDataBackend.entity.FinancialRecord;
import com.example.FinanceDataBackend.service.FinancialRecordService;

@RestController
@RequestMapping("/api/records")
public class FinancialRecordController {

    @Autowired
    private FinancialRecordService service;

    // ✅ CREATE
    @PostMapping
    public ResponseEntity<?> create(@RequestBody FinancialRecordDTO dto) {
        return ResponseEntity.ok(service.createRecord(dto));
    }

    // ✅ GET ALL
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAllRecords());
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getRecordById(id));
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody FinancialRecordDTO dto) {
        return ResponseEntity.ok(service.updateRecord(id, dto));
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteRecord(id);
        return ResponseEntity.ok("Record deleted successfully");
    }
    // ✅ FILER
    @GetMapping("/filter")
    public ResponseEntity<?> filterRecords(
            @RequestParam(required = false) RecordType type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {

        LocalDate start = (startDate != null) ? LocalDate.parse(startDate) : null;
        LocalDate end = (endDate != null) ? LocalDate.parse(endDate) : null;

        return ResponseEntity.ok(
                service.filterRecords(type, category, start, end)
        );
    }
}