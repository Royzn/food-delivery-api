package com.example.food_delivery_api.dto.courier;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourierResponse {

    @NotBlank
    @JsonProperty("name")
    private String name;

    @NotNull
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
