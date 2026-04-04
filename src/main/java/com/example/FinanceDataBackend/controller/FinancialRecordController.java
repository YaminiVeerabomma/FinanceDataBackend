package com.example.FinanceDataBackend.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.FinanceDataBackend.Enum.RecordType;
import com.example.FinanceDataBackend.entity.FinancialRecord;
import com.example.FinanceDataBackend.exception.AccessDeniedException;
import com.example.FinanceDataBackend.service.FinancialRecordService;

@RestController
@RequestMapping("/records")
public class FinancialRecordController {

    @Autowired
    private FinancialRecordService service;

    // GET RECORDS (Analyst + Admin)
    @GetMapping
    public List<FinancialRecord> getAll(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) LocalDate start,
            @RequestParam(required = false) LocalDate end,
            @RequestParam(required = false) RecordType type,
            Authentication auth) {

        String role = auth.getAuthorities().stream().findFirst().get().getAuthority();
        if (role.equals("ROLE_VIEWER")) {
            throw new AccessDeniedException("Viewers cannot access records.");
        }

        return service.filter(category, start, end, type);
    }

    // CREATE RECORD (Admin Only)
    @PostMapping
    public FinancialRecord create(@RequestBody FinancialRecord record, Authentication auth) {
        String role = auth.getAuthorities().stream().findFirst().get().getAuthority();
        if (!role.equals("ROLE_ADMIN")) {
            throw new AccessDeniedException("Only Admin can create financial records.");
        }
        return service.save(record);
    }

    // DELETE RECORD (Admin Only)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Authentication auth) {
        String role = auth.getAuthorities().stream().findFirst().get().getAuthority();
        if (!role.equals("ROLE_ADMIN")) {
            throw new AccessDeniedException("Only Admin can delete financial records.");
        }
        service.delete(id);
    }

    // UPDATE RECORD (Admin Only)
    @PutMapping("/{id}")
    public FinancialRecord update(@PathVariable Long id, @RequestBody FinancialRecord record, Authentication auth) {
        String role = auth.getAuthorities().stream().findFirst().get().getAuthority();
        if (!role.equals("ROLE_ADMIN")) {
            throw new AccessDeniedException("Only Admin can update financial records.");
        }
        return service.update(id, record);
    }
}