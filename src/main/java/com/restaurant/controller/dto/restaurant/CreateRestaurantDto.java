package com.restaurant.controller.dto.restaurant;

import com.restaurant.model.RestaurantType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRestaurantDto {

    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotNull
    @Enumerated(EnumType.STRING)
    private RestaurantType type;
}
