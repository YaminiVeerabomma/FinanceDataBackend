package com.example.FinanceDataBackend.service;



import com.example.FinanceDataBackend.entity.User;
import com.example.FinanceDataBackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id).orElseThrow();
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setRole(updatedUser.getRole());
        user.setActive(updatedUser.isActive());
        return userRepository.save(user);
    }

    public User updateStatus(Long id, boolean active) {
        User user = userRepository.findById(id).orElseThrow();
        user.setActive(active);
        return userRepository.save(user);
    }
}