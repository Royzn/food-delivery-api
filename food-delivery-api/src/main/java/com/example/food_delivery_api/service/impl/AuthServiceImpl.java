package com.example.food_delivery_api.service.impl;

import com.example.food_delivery_api.dto.auth.AuthResponse;
import com.example.food_delivery_api.entity.AppUserEntity;
import com.example.food_delivery_api.repository.AppUserRepository;
import com.example.food_delivery_api.service.AuthService;
import com.example.food_delivery_api.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(String username, String password){
        appUserRepository.findByUsername(username).ifPresent(u -> {
            throw new IllegalArgumentException("Username already exist");
        });

        AppUserEntity user = AppUserEntity.builder()
                .username(username)
                .passwordHash(passwordEncoder.encode(password))
                .roles(new HashSet<>(Collections.singletonList("CUSTOMER")))
                .build();

        appUserRepository.save(user);

        String token = jwtService.generateToken(user.getUsername(), Map.of("roles", user.getRoles()));

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .expireInMs(jwtService.getExpirationMs())
                .build();
    }

    @Override
    public AuthResponse login(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        Set<String> roles = appUserRepository.findByUsername(username).map(
                AppUserEntity::getRoles
        ).orElseGet(()-> new HashSet<>(Collections.singletonList("ROLE_USER")));

        String token = jwtService.generateToken(username, Map.of("roles", roles));

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .expireInMs(jwtService.getExpirationMs())
                .build();
    }
}
