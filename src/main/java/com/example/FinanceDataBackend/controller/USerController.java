package com.example.FinanceDataBackend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FinanceDataBackend.entity.User;
import com.example.FinanceDataBackend.service.UserService;

@RestController
@RequestMapping("/users")
public class USerController {

    private final UserService service;

    public USerController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return service.createUser(user);
    }

    @GetMapping
    public List<User> getAll() {
        return service.getAllUsers();
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        return service.updateUser(id, user);
    }

    @PatchMapping("/{id}/status")
    public User status(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
        return service.updateStatus(id, body.get("active"));
    }
}