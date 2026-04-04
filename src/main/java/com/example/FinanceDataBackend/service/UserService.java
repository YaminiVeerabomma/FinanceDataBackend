package com.example.FinanceDataBackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.FinanceDataBackend.Enum.Role;
import com.example.FinanceDataBackend.Enum.UserStatus;
import com.example.FinanceDataBackend.entity.User;
import com.example.FinanceDataBackend.exception.CustomException;
import com.example.FinanceDataBackend.repository.UserRepository;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    // ✅ CREATE USER
    public User createUser(User user) {

        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new CustomException("User already exists");
        }

        user.setPassword(encoder.encode(user.getPassword()));

        // Default values
        if (user.getRole() == null) {
            user.setRole(Role.VIEWER);
        }

        if (user.getStatus() == null) {
            user.setStatus(UserStatus.ACTIVE);
        }

        return userRepo.save(user);
    }

    // ✅ GET ALL USERS (Admin only)
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // ✅ GET USER BY ID
    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new CustomException("User not found"));
    }

    // ✅ UPDATE USER ROLE
    public User updateUserRole(Long id, Role role) {

        User user = getUserById(id);
        user.setRole(role);

        return userRepo.save(user);
    }

    // ✅ ACTIVATE USER
    public User activateUser(Long id) {

        User user = getUserById(id);
        user.setStatus(UserStatus.ACTIVE);

        return userRepo.save(user);
    }

    // ✅ DEACTIVATE USER
    public User deactivateUser(Long id) {

        User user = getUserById(id);
        user.setStatus(UserStatus.INACTIVE);

        return userRepo.save(user);
    }

    // ✅ DELETE USER
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}