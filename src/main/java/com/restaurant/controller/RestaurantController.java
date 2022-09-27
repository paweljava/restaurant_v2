package com.restaurant.controller;

import com.restaurant.model.CreateRestaurantDto;
import com.restaurant.model.Restaurant;
import com.restaurant.model.UpdateRestaurantDto;
import com.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Restaurant createRestaurant(@RequestBody CreateRestaurantDto createRestaurantDto) {
        return restaurantService.restaurantAdd(createRestaurantDto);
    }

    @GetMapping()
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{uuid}")
    public Restaurant getSingleRestaurantById(@PathVariable("uuid") UUID uuid) {
        return restaurantService.getSingleRestaurantById(uuid);
    }

    @PutMapping("/{uuid}")
    public Restaurant updateRestaurant(@PathVariable("uuid") UUID uuid, @RequestBody UpdateRestaurantDto updateRestaurantDto) {
        return restaurantService.updateRestaurant(uuid, updateRestaurantDto);
    }

    @DeleteMapping("/{uuid}")
    public void deleteRestaurant(@PathVariable("uuid") UUID uuid) {
        restaurantService.deleteRestaurant(uuid);
    }
}


