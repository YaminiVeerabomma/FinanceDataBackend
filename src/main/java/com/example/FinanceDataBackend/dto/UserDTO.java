package com.example.FinanceDataBackend.dto;

import com.example.FinanceDataBackend.Enum.Role;
import com.example.FinanceDataBackend.Enum.UserStatus;

import lombok.Data;

@Data
public class UserDTO {

    private String name;
    private String email;
    private String password;
    private Role role;
    private UserStatus status;
}