package com.example.food_delivery_api.service;

import com.example.food_delivery_api.entity.AppUserEntity;
import com.example.food_delivery_api.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final AppUserRepository appUserRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUserEntity u = appUserRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found" + username)
        );

        return new User(
                u.getUsername(), u.getPasswordHash(), u.getRoles().stream()
                .map(SimpleGrantedAuthority::new).collect(Collectors.toSet())
        );
    }
}
