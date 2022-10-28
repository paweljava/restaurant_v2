package com.restaurant.controller.dto.restaurant;

import com.restaurant.controller.dto.meal.MealDto;
import com.restaurant.model.RestaurantType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Data
@Getter
@NoArgsConstructor
public class RestaurantDto {

    private String name;
    private String address;
    @Enumerated(EnumType.STRING)
    private RestaurantType type;
    private List<MealDto> mealsDto;

    public RestaurantDto(String name, String address, RestaurantType type, List<MealDto> mealsDto) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.mealsDto = mealsDto;
    }

    public RestaurantDto(String name, String address, RestaurantType type) {
        this.name = name;
        this.address = address;
        this.type = type;
    }
}
