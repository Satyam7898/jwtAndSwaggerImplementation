package com.example.demo.storeUser;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private String username;
    private String password;
    private Set<String> roles; // Role names as strings

    // Getters and Setters
}
