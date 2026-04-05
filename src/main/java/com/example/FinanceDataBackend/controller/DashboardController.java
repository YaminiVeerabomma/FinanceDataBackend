package com.example.FinanceDataBackend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FinanceDataBackend.service.DashboardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Dashboard", description = "Financial summary APIs")
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService service;

    @Operation(summary = "Get income, expense, balance summary")
    @PreAuthorize("hasAnyAuthority('VIEWER','ANALYST','ADMIN')")
    @GetMapping
    public Map<String, Double> summary() {

        Map<String, Double> map = new HashMap<>();
        map.put("income", service.totalIncome());
        map.put("expense", service.totalExpense());
        map.put("balance", service.balance());

        return map;
    }
}