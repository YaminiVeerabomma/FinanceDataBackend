package com.example.FinanceDataBackend.controller;



import com.example.FinanceDataBackend.dto.AuthRequest;
import com.example.FinanceDataBackend.dto.AuthResponse;
import com.example.FinanceDataBackend.entity.User;

import com.example.FinanceDataBackend.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;


@Tag(name = "Authentication", description = "Login & Register APIs")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) { this.service = service; }
    @Operation(summary = "Register new user")
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return service.register(user);
    }
    @Operation(summary = "Login and get JWT token")
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return new AuthResponse(service.login(request));
    }
}