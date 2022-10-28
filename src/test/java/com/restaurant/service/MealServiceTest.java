package com.restaurant.service;

import com.restaurant.controller.dto.meal.CreateMealDto;
import com.restaurant.controller.dto.meal.UpdateMealDto;
import com.restaurant.model.Meal;
import com.restaurant.model.Restaurant;
import com.restaurant.repository.MealRepository;
import com.restaurant.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.restaurant.model.RestaurantType.MEDITERRANEAN;
import static com.restaurant.util.Optionality.some;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MealServiceTest {

    private final RestaurantRepository restaurantRepository = mock(RestaurantRepository.class);
    private final MealRepository mealRepository = mock(MealRepository.class);
    private final MealService mealService = new MealService(restaurantRepository, mealRepository);

    @Test
    void should_add_meal_to_restaurant() {
        // given
        final var restaurant = new Restaurant("Nazwa", "Adres", MEDITERRANEAN);
        final var createMealDto = new CreateMealDto("Meal", (float) 33.6);
        final var meal = new Meal(restaurant.getId(), createMealDto.getName(), createMealDto.getPrice());

        given(restaurantRepository.findById(restaurant.getId())).willReturn(Optional.of(restaurant));
        given(mealRepository.save(any())).willReturn(meal);

        // when
        final var result = mealService.addMealToRestaurant(restaurant.getId(), createMealDto);

        // then
        assertEquals(restaurant.getId(), result.getRestaurantId());
        assertEquals(meal.getId(), result.getId());
        assertEquals(meal.getRestaurantId(), result.getRestaurantId());
        assertEquals(meal.getName(), result.getName());
        assertEquals(meal.getPrice(), result.getPrice());
        verify(mealRepository).save(any());
        verify(mealRepository).save(argThat(
                r -> r.getName().equals(meal.getName())));
    }

    @Test
    void should_update_meal_all_fields() {
        // given
        final var randomRestaurantId = UUID.randomUUID();
        List<Meal> meals = List.of(new Meal(randomRestaurantId, "Meal1", (float) 19));
        final var restaurant = new Restaurant(randomRestaurantId, "Nazwa", "Adres", MEDITERRANEAN, meals);
        final var meal = restaurant.getMeals().get(0);
        final var updateMealDto = new UpdateMealDto(some("Updating name"), some((float) 39.9));
        final var updatedMeal = new Meal(meal.getId(), restaurant.getId(), "Updating name", (float) 39.9);

        given(restaurantRepository.findById(restaurant.getId())).willReturn(Optional.of(restaurant));
        given(mealRepository.findById(Optional.of(meal.getId()).get())).willReturn(Optional.of(meal));
        given(mealRepository.save(updatedMeal)).willReturn(updatedMeal);

        // when
        final var result = mealService.updateMeal(restaurant.getId(), meal.getId(), updateMealDto);

        // then
        assertEquals(updatedMeal, result);
        assertEquals(updatedMeal.getId(), result.getId());
        assertEquals(restaurant.getId(), result.getRestaurantId());
        assertEquals(updatedMeal.getName(), result.getName());
        assertEquals(updatedMeal.getPrice(), result.getPrice());
        verify(restaurantRepository).findById(restaurant.getId());
        verify(mealRepository).findById(meal.getId());
        verify(mealRepository).save(argThat(
                m -> m.getRestaurantId().equals(restaurant.getId()) &&
                        m.getName().equals(updatedMeal.getName()))
        );
    }

    @Test
    void should_not_update_meal_name() {
        // given
        final var randomRestaurantId = UUID.randomUUID();
        List<Meal> meals = List.of(new Meal(randomRestaurantId, "Meal1", (float) 19));
        final var restaurant = new Restaurant(randomRestaurantId, "Nazwa", "Adres", MEDITERRANEAN, meals);
        final var meal = restaurant.getMeals().get(0);
        final var updateMealDto = new UpdateMealDto(Optional.empty(), some((float) 39.9));
        final var updatedMeal = new Meal(meal.getId(), restaurant.getId(), "Meal1", (float) 39.9);

        given(restaurantRepository.findById(restaurant.getId())).willReturn(Optional.of(restaurant));
        given(mealRepository.findById(Optional.of(meal.getId()).get())).willReturn(Optional.of(meal));
        given(mealRepository.save(updatedMeal)).willReturn(updatedMeal);

        // when
        final var result = mealService.updateMeal(restaurant.getId(), meal.getId(), updateMealDto);

        // then
        assertEquals(updatedMeal, result);
        assertEquals(updatedMeal.getId(), result.getId());
        assertEquals(restaurant.getId(), result.getRestaurantId());
        assertEquals(updatedMeal.getName(), result.getName());
        assertEquals(updatedMeal.getPrice(), result.getPrice());
        verify(restaurantRepository).findById(restaurant.getId());
        verify(mealRepository).findById(meal.getId());
        verify(mealRepository).save(argThat(
                m -> m.getRestaurantId().equals(restaurant.getId()) &&
                        m.getName().equals(updatedMeal.getName()))
        );
    }

    @Test
    void should_not_update_meal_price() {
        // given
        final var randomRestaurantId = UUID.randomUUID();
        List<Meal> meals = List.of(new Meal(randomRestaurantId, "Meal1", (float) 19));
        final var restaurant = new Restaurant(randomRestaurantId, "Nazwa", "Adres", MEDITERRANEAN, meals);
        final var meal = restaurant.getMeals().get(0);
        final var updateMealDto = new UpdateMealDto(some("Updating name"), Optional.empty());
        final var updatedMeal = new Meal(meal.getId(), restaurant.getId(), "Updating name", (float) 19);

        given(restaurantRepository.findById(restaurant.getId())).willReturn(Optional.of(restaurant));
        given(mealRepository.findById(Optional.of(meal.getId()).get())).willReturn(Optional.of(meal));
        given(mealRepository.save(updatedMeal)).willReturn(updatedMeal);

        // when
        final var result = mealService.updateMeal(restaurant.getId(), meal.getId(), updateMealDto);

        // then
        assertEquals(updatedMeal, result);
        assertEquals(updatedMeal.getId(), result.getId());
        assertEquals(restaurant.getId(), result.getRestaurantId());
        assertEquals(updatedMeal.getName(), result.getName());
        assertEquals(updatedMeal.getPrice(), result.getPrice());
        verify(restaurantRepository).findById(restaurant.getId());
        verify(mealRepository).findById(meal.getId());
        verify(mealRepository).save(argThat(
                m -> m.getRestaurantId().equals(restaurant.getId()) &&
                        m.getName().equals(updatedMeal.getName()))
        );
    }

    @Test
    void should_delete_meal() {
        // given
        final var restaurantId = UUID.randomUUID();
        List<Meal> meals = List.of(new Meal(restaurantId, "Meal1", (float) 29.3));
        final var restaurant = new Restaurant(restaurantId, "Nazwa", "Adres", MEDITERRANEAN, meals);
        final var meal = restaurant.getMeals().get(0);

        given(restaurantRepository.findById(restaurant.getId())).willReturn(Optional.of(restaurant));
        given(mealRepository.findById(Optional.of(meal.getId()).get())).
                willReturn(Optional.of(meal)).
                willReturn(Optional.empty());

        // when
        final var result = mealService.deleteMeal(restaurant.getId(), meal.getId());

        // then
        assertTrue(result);
        verify(mealRepository).deleteById(meal.getId());
    }
}
