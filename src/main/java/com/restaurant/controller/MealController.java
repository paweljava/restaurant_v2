package com.restaurant.controller;

import com.restaurant.controller.mapper.MealMapper;
import com.restaurant.controller.dto.meal.CreateMealDto;
import com.restaurant.controller.dto.meal.MealDto;
import com.restaurant.controller.dto.meal.UpdateMealDto;
import com.restaurant.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants/")
public class MealController {

    private final MealService mealService;

    @Autowired
    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @PostMapping("{id}/meals")
    public MealDto addMealToRestaurant(@Valid @PathVariable("id") UUID id, @RequestBody CreateMealDto createMealDto) {
        return MealMapper.mapMealToMealDto(mealService.addMealToRestaurant(id, createMealDto));
    }

    @PutMapping("{id}/meals/{mealid}")
    public MealDto updateMeal(@PathVariable("id") UUID id, @PathVariable("mealid") UUID mealId, @RequestBody UpdateMealDto updateMealDto) {
        return MealMapper.mapMealToMealDto(mealService.updateMeal(id, mealId, updateMealDto));
    }

    @DeleteMapping("{id}/meals/{mealid}")
    public boolean deleteMeal(@PathVariable("id") UUID id, @PathVariable("mealid") UUID mealId) {
        return mealService.deleteMeal(id, mealId);
    }
}