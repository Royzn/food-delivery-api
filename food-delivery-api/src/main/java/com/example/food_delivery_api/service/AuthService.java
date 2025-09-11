package com.example.food_delivery_api.service;

import com.example.food_delivery_api.dto.auth.AuthResponse;

public interface AuthService {
    AuthResponse register(String username, String password);
    AuthResponse login(String username, String password);
}
