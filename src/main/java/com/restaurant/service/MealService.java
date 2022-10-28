package com.restaurant.service;

import com.restaurant.common.ExceptionMessages;
import com.restaurant.controller.dto.meal.CreateMealDto;
import com.restaurant.controller.dto.meal.UpdateMealDto;
import com.restaurant.model.Meal;
import com.restaurant.model.Restaurant;
import com.restaurant.repository.MealRepository;
import com.restaurant.repository.RestaurantRepository;
import com.restaurant.util.Checks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class MealService {

    private final RestaurantRepository restaurantRepository;
    private final MealRepository mealRepository;

    @Autowired
    public MealService(RestaurantRepository restaurantRepository, MealRepository mealRepository) {
        this.restaurantRepository = restaurantRepository;
        this.mealRepository = mealRepository;
    }

    public Meal addMealToRestaurant(UUID id, CreateMealDto createMealDto) {
        final var restaurant = findRestaurant(id);
        final var meal = new Meal(restaurant.getId(), createMealDto.getName(), createMealDto.getPrice());

        return mealRepository.save(meal);
    }

    public Meal updateMeal(UUID id, UUID mealId, UpdateMealDto request) {
        final var restaurant = findRestaurant(id);
        final var meal = findMeal(mealId);

        Checks.checkThat(restaurant.getMeals().contains(meal),
                ExceptionMessages.MISSING_MEAL_IN_RESTAURANT_MENU_EXCEPTION_MESSAGE);
        Checks.checkThat(atLeastOneFieldPresent(request),
                ExceptionMessages.AT_LEAST_ONE_PROPERTY_SHOULD_BE_PRESENT_EXCEPTION_MESSAGE);

        return mealRepository.save(new Meal(
                meal.getId(),
                meal.getRestaurantId(),
                request.getName().orElse(meal.getName()),
                request.getPrice().orElse(meal.getPrice())));
    }

    public boolean deleteMeal(UUID id, UUID mealId) {
        final var restaurant = findRestaurant(id);
        final var meal = findMeal(mealId);

        Checks.checkThat(restaurant.getMeals().contains(meal),
                ExceptionMessages.MISSING_MEAL_IN_RESTAURANT_MENU_EXCEPTION_MESSAGE);
        mealRepository.deleteById(mealId);

        return mealRepository.findAllById(Collections.singleton(mealId)).isEmpty();
    }

    private Restaurant findRestaurant(UUID id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalStateException(ExceptionMessages.RESTAURANT_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    private Meal findMeal(UUID id) {
        return mealRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(ExceptionMessages.MEAL_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    private boolean atLeastOneFieldPresent(UpdateMealDto request) {
        return request.getName().isPresent() || request.getPrice().isPresent();
    }
}
