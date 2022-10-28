package com.restaurant.controller;

import com.restaurant.controller.dto.restaurant.CreateRestaurantDto;
import com.restaurant.controller.dto.restaurant.RestaurantDto;
import com.restaurant.controller.dto.restaurant.UpdateRestaurantDto;
import com.restaurant.service.RestaurantService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static com.restaurant.controller.mapper.RestaurantMapper.*;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    private RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public CreateRestaurantDto createRestaurant(@Valid @RequestBody CreateRestaurantDto createRestaurantDto) {
        return mapRestaurantToCreateRestaurantDto(restaurantService.addRestaurant(createRestaurantDto));
    }

    @GetMapping
    public List<RestaurantDto> getAllRestaurants() {
        return mapRestaurantsToRestaurantDtoList(restaurantService.getAllRestaurants());
    }

    @GetMapping("/{id}")
    public RestaurantDto getRestaurantById(@PathVariable("id") UUID id) {
        return mapRestaurantToRestaurantDto(restaurantService.getRestaurantById(id));
    }

    @PutMapping("/{id}")
    public RestaurantDto updateRestaurant(@PathVariable("id") UUID id,
                                          @RequestBody UpdateRestaurantDto updateRestaurantDto) {
        return mapRestaurantToRestaurantDto(restaurantService.updateRestaurant(id, updateRestaurantDto));
    }

    @DeleteMapping("/{id}")
    public boolean deleteRestaurant(@PathVariable("id") UUID id) {
        return restaurantService.deleteRestaurant(id);
    }
}


