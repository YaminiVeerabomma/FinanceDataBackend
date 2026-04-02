package com.example.FinanceDataBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FinanceDataBackend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
