package com.example.FinanceDataBackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.FinanceDataBackend.Enum.Role;
import com.example.FinanceDataBackend.entity.User;
import com.example.FinanceDataBackend.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    // ✅ Admin only
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public User create(@RequestBody User user) {
        return service.createUser(user);
    }

    // ✅ Admin only
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<User> getAll() {
        return service.getAllUsers();
    }

    // ✅ Admin only
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/role")
    public User updateRole(@PathVariable Long id, @RequestParam Role role) {
        return service.updateUserRole(id, role);
    }

    // ✅ Admin only
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/activate")
    public User activate(@PathVariable Long id) {
        return service.activateUser(id);
    }

    // ✅ Admin only
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/deactivate")
    public User deactivate(@PathVariable Long id) {
        return service.deactivateUser(id);
    }

    // ✅ Admin only
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteUser(id);
    }
}