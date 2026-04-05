package com.example.FinanceDataBackend.service;



import com.example.FinanceDataBackend.entity.FinancialRecord;
import com.example.FinanceDataBackend.repository.FinancialRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FinancialRecordService {

    @Autowired
    private FinancialRecordRepository repo;

    public FinancialRecord save(FinancialRecord record) {
        return repo.save(record);
    }

    public List<FinancialRecord> getAll() {
        return repo.findAll();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<FinancialRecord> filter(String category, LocalDate start, LocalDate end, String type) {
        List<FinancialRecord> all = repo.findAll();
        return all.stream()
                .filter(r -> category == null || r.getCategory().equalsIgnoreCase(category))
                .filter(r -> type == null || r.getType().toString().equalsIgnoreCase(type))
                .filter(r -> start == null || !r.getDate().isBefore(start))
                .filter(r -> end == null || !r.getDate().isAfter(end))
                .toList();
    }

    public FinancialRecord update(Long id, FinancialRecord newRecord) {
        FinancialRecord existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
        existing.setAmount(newRecord.getAmount());
        existing.setType(newRecord.getType());
        existing.setCategory(newRecord.getCategory());
        existing.setDate(newRecord.getDate());
        existing.setNotes(newRecord.getNotes());
        return repo.save(existing);
    }

    // ✅ Pagination
    public Page<FinancialRecord> getPaginated(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("date").descending());
        return repo.findAll(pageable);
    }
}