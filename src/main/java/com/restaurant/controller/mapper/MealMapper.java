package com.restaurant.controller.mapper;

import com.restaurant.controller.dto.meal.MealDto;
import com.restaurant.model.Meal;
import com.restaurant.model.Restaurant;

import java.util.List;
import java.util.stream.Collectors;

public class MealMapper {

    public static List<MealDto> mapMealToMealDto(Restaurant restaurant) {
        return restaurant.getMeals().stream().
                map(meal -> new MealDto(
                        meal.getName(),
                        meal.getPrice())).
                collect(Collectors.toList());
    }

    public static MealDto mapMealToMealDto(Meal meal) {
        return new MealDto(
                        meal.getName(),
                        meal.getPrice());
    }
}
