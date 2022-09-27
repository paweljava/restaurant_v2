package com.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRestaurantDto {

    private String name;
    private String address;
    @Enumerated(EnumType.STRING)
    private RestaurantType type;
}
