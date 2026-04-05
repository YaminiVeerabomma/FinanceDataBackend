package com.example.FinanceDataBackend.controller;

import com.example.FinanceDataBackend.dto.AuthRequest;
import com.example.FinanceDataBackend.dto.AuthResponse;
import com.example.FinanceDataBackend.entity.User;
import com.example.FinanceDataBackend.service.AuthService;
import com.example.FinanceDataBackend.service.LoginAttemptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Login & Register APIs")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final LoginAttemptService loginAttemptService;

    public AuthController(AuthService authService, LoginAttemptService loginAttemptService) {
        this.authService = authService;
        this.loginAttemptService = loginAttemptService;
    }

    @Operation(summary = "Register new user")
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return authService.register(user);
    }

    @Operation(summary = "Login and get JWT token")
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        String key = request.getEmail(); // track per user email

        // Block if too many failed attempts
        if (loginAttemptService.isBlocked(key)) {
            throw new RuntimeException("Too many login attempts. Please try again later.");
        }

        try {
            // attempt login
            String token = authService.login(request);
            loginAttemptService.loginSucceeded(key); // reset counter on success
            return new AuthResponse(token);
        } catch (Exception e) {
            // increment failed attempts
            loginAttemptService.loginFailed(key);
            throw e; // propagate exception
        }
    }
}