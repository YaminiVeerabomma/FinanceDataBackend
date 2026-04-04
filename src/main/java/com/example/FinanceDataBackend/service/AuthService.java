package com.example.FinanceDataBackend.service;

import com.example.FinanceDataBackend.dto.AuthRequest;
import com.example.FinanceDataBackend.entity.User;
import com.example.FinanceDataBackend.repository.UserRepository;
import com.example.FinanceDataBackend.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    public AuthService(UserRepository repo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authManager) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
    }

    public String register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);
        return "User registered successfully";
    }

    public String login(AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = repo.findByEmail(request.getEmail()).orElseThrow();
        return jwtUtil.generateToken(user);
    }
}