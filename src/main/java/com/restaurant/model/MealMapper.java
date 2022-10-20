package com.restaurant.model;

import com.restaurant.dto.meal.MealDto;

public class MealMapper {

    public static MealDto mealMapper(Meal meal) {
        return new MealDto(
                meal.getName(),
                meal.getPrice());
    }
}
