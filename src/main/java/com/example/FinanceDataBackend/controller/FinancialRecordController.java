package com.example.FinanceDataBackend.controller;

import com.example.FinanceDataBackend.dto.PageRequestDTO;
import com.example.FinanceDataBackend.entity.FinancialRecord;
import com.example.FinanceDataBackend.exception.AccessDeniedException;
import com.example.FinanceDataBackend.service.FinancialRecordService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Financial Records", description = "Manage financial records")
@RestController
@RequestMapping("/records")
public class FinancialRecordController {

    @Autowired
    private FinancialRecordService service;

    @Operation(summary = "Get all financial records with filters (Analyst & Admin)")
    @GetMapping
    public List<FinancialRecord> getAll(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) LocalDate start,
            @RequestParam(required = false) LocalDate end,
            @RequestParam(required = false) String type,
            Authentication auth) {

        String role = auth.getAuthorities().stream().findFirst().get().getAuthority();
        if (role.equals("ROLE_VIEWER")) {
            throw new AccessDeniedException("Viewers cannot access records.");
        }

        return service.filter(category, start, end, type);
    }

    @Operation(summary = "Create financial record (Admin only)")
    @PostMapping
    public FinancialRecord create(@RequestBody FinancialRecord record, Authentication auth) {
        String role = auth.getAuthorities().stream().findFirst().get().getAuthority();
        if (!role.equals("ROLE_ADMIN")) {
            throw new AccessDeniedException("Only Admin can create financial records.");
        }
        return service.save(record);
    }

    @Operation(summary = "Delete financial record (Admin only)")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Authentication auth) {
        String role = auth.getAuthorities().stream().findFirst().get().getAuthority();
        if (!role.equals("ROLE_ADMIN")) {
            throw new AccessDeniedException("Only Admin can delete financial records.");
        }
        service.delete(id);
    }

    @Operation(summary = "Update financial record (Admin only)")
    @PutMapping("/{id}")
    public FinancialRecord update(@PathVariable Long id, @RequestBody FinancialRecord record, Authentication auth) {
        String role = auth.getAuthorities().stream().findFirst().get().getAuthority();
        if (!role.equals("ROLE_ADMIN")) {
            throw new AccessDeniedException("Only Admin can update financial records.");
        }
        return service.update(id, record);
    }

    // ✅ Pagination API
    @PostMapping("/page")
    public Page<FinancialRecord> getPaginated(@RequestBody PageRequestDTO requestDTO, Authentication auth) {
        String role = auth.getAuthorities().stream().findFirst().get().getAuthority();
        if (role.equals("ROLE_VIEWER")) {
            throw new AccessDeniedException("Viewers cannot access financial records.");
        }
        return service.getPaginated(requestDTO.getPage(), requestDTO.getSize());
    }
}