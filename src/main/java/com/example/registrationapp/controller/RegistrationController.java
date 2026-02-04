package com.example.registrationapp.controller;

import com.example.registrationapp.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class RegistrationController {

    private final List<User> users = new ArrayList<>();

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().body("Username is required");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Password is required");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }
        if (users.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        users.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}