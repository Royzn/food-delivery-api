package com.example.food_delivery_api.controller;

import com.example.food_delivery_api.dto.auth.AuthRequest;
import com.example.food_delivery_api.dto.auth.AuthResponse;
import com.example.food_delivery_api.dto.auth.RegisterRequest;
import com.example.food_delivery_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req){
        return ResponseEntity.ok(authService.register(req.getUsername().trim().toLowerCase(), req.getPassword()));
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest req){
        return ResponseEntity.ok(authService.login(req.getUsername().trim().toLowerCase(), req.getPassword()));
    }
}
