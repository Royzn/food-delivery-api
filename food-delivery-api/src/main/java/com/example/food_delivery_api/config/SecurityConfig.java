package com.example.food_delivery_api.config;

import com.example.food_delivery_api.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailService userDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/restaurants/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/restaurants/*/menus").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/restaurants").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/restaurants/*/menus").hasAnyRole("RESTAURANT")
                                .requestMatchers(HttpMethod.GET, "/api/orders/**").hasAnyRole("CUSTOMER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/orders/**").hasRole("CUSTOMER")
                                .requestMatchers(HttpMethod.PUT, "/api/orders/*/status").hasAnyRole("RESTAURANT", "COURIER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/couriers/**").hasAnyRole("COURIER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/couriers").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/customers/**").hasAnyRole("CUSTOMER", "ADMIN")
                                .requestMatchers("/actuator/health").permitAll()
                                .anyRequest().authenticated()
                        )
                .authenticationProvider(daoAuthenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
}
