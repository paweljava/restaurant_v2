package com.restaurant.controller;

import com.restaurant.service.MealService;
import com.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;

public class MealController {

    private final MealService mealService;

    @Autowired
    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

}
