package com.restaurant.controller;

import com.restaurant.dto.meal.CreateMealDto;
import com.restaurant.model.Meal;
import com.restaurant.dto.meal.UpdateMealDto;
import com.restaurant.service.MealService;
import com.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants/")
public class MealController {

    private final MealService mealService;
    private final RestaurantService restaurantService;

    @Autowired
    public MealController(MealService mealService, RestaurantService restaurantService) {
        this.mealService = mealService;
        this.restaurantService = restaurantService;
    }

    @PostMapping("{id}/meals")
    public Meal addMealToRestaurant(@Valid @PathVariable("id") UUID id, @RequestBody CreateMealDto createMealDto) {
        return mealService.addMealToRestaurant(id, createMealDto);
    }

    @PutMapping("{id}/meals/{mealid}")
    public Meal updateMeal(@PathVariable("id") UUID id, @PathVariable ("mealid") UUID mealId, @RequestBody UpdateMealDto updateMealDto) {
        return mealService.updateMeal(id, mealId, updateMealDto);
    }

    @DeleteMapping("{id}/meals/{mealid}")
    public boolean deleteMeal(@PathVariable("id") UUID id, @PathVariable("mealid")UUID mealId) {
        return mealService.deleteMeal(id, mealId);
    }
}
