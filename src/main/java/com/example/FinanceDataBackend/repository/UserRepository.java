package com.example.FinanceDataBackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FinanceDataBackend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	  Optional<User> findByEmail(String email);
}
