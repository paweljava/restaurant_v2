package com.restaurant.controller;

import com.restaurant.dto.restaurant.CreateRestaurantDto;
import com.restaurant.dto.restaurant.RestaurantDto;
import com.restaurant.dto.restaurant.UpdateRestaurantDto;
import com.restaurant.model.Restaurant;
import com.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public Restaurant createRestaurant(@Valid @RequestBody CreateRestaurantDto createRestaurantDto) {
        return restaurantService.addRestaurant(createRestaurantDto);
    }

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public RestaurantDto getRestaurantById(@PathVariable("id") UUID id) {
        return restaurantService.getRestaurantById(id);
    }

    @PutMapping("/{id}")
    // poprawic zwracanie dto
    public Restaurant updateRestaurant(@PathVariable("id") UUID id, @RequestBody UpdateRestaurantDto updateRestaurantDto) {
        return restaurantService.updateRestaurant(id, updateRestaurantDto);
    }

    @DeleteMapping("/{id}")
    public boolean deleteRestaurant(@PathVariable("id") UUID id) {
        return restaurantService.deleteRestaurant(id);
    }
}


