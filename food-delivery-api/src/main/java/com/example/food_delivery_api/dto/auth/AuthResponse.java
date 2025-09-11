package com.example.food_delivery_api.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expire_in_ms")
    private Long expireInMs;
}
