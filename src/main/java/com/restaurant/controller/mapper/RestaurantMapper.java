package com.restaurant.controller.mapper;

import com.restaurant.controller.dto.restaurant.CreateRestaurantDto;
import com.restaurant.controller.dto.restaurant.RestaurantDto;
import com.restaurant.model.Restaurant;

import java.util.List;
import java.util.stream.Collectors;

import static com.restaurant.controller.mapper.MealMapper.mapMealToMealDto;

public class RestaurantMapper {

    public static List<RestaurantDto> mapRestaurantsToRestaurantDtoList(List<Restaurant> restaurants) {
        return restaurants.stream().map(restaurant -> new RestaurantDto(
                        restaurant.getName(),
                        restaurant.getAddress(),
                        restaurant.getType(),
                        mapMealToMealDto(restaurant)))
                .collect(Collectors.toList());
    }

    public static RestaurantDto mapRestaurantToRestaurantDto(Restaurant restaurant) {
        return new RestaurantDto(
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getType(),
                mapMealToMealDto(restaurant));
    }

    public static CreateRestaurantDto mapRestaurantToCreateRestaurantDto(Restaurant restaurant) {
        return new CreateRestaurantDto(
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getType());
    }
}
