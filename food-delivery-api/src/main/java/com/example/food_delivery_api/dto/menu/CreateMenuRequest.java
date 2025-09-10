package com.example.food_delivery_api.dto.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMenuRequest {
    @NotBlank
    @JsonProperty("menu_name")
    private String menuName;

    @NotNull
    @DecimalMin("0.00")
    @JsonProperty("menu_price")
    private Double menuPrice;
}
