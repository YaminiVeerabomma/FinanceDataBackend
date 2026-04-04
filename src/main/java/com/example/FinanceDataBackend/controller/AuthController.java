package com.example.FinanceDataBackend.controller;



import com.example.FinanceDataBackend.dto.AuthRequest;
import com.example.FinanceDataBackend.dto.AuthResponse;
import com.example.FinanceDataBackend.entity.User;

import com.example.FinanceDataBackend.service.AuthService;



import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) { this.service = service; }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return service.register(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return new AuthResponse(service.login(request));
    }
}