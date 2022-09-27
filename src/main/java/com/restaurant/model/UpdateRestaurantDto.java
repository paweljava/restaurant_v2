package com.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRestaurantDto {

    private String name;
    private String address;
    @Enumerated(EnumType.STRING)
    private RestaurantType type;
}
