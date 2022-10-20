package com.restaurant.model;

import com.restaurant.dto.restaurant.RestaurantDto;

import java.util.stream.Collectors;

public class RestaurantMapper {

    public static RestaurantDto restaurantMapper(Restaurant restaurant) {
        return new RestaurantDto(
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getType(),
                restaurant.getMeals().stream().
                        map(MealMapper::mealMapper).
                        collect(Collectors.toList()));
    }
}
