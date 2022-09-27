package com.restaurant.service;

import com.restaurant.model.CreateRestaurantDto;
import com.restaurant.model.Restaurant;
import com.restaurant.model.UpdateRestaurantDto;
import com.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant restaurantAdd(CreateRestaurantDto createRestaurantDto) {
        final var restaurant = new Restaurant(
                createRestaurantDto.getName(),
                createRestaurantDto.getAddress(),
                createRestaurantDto.getType()
        );
        return restaurantRepository.save(restaurant);
    }

    public Restaurant getSingleRestaurantById(UUID uuid) {
        return restaurantRepository.findById(uuid).orElseThrow();
    }

    public Restaurant updateRestaurant(UUID uuid, UpdateRestaurantDto updateRestaurantDto) {
        var updatingRestaurant = restaurantRepository.findById(uuid).orElseThrow();
        updatingRestaurant.setName(updateRestaurantDto.getName());
        updatingRestaurant.setAddress(updateRestaurantDto.getAddress());
        updatingRestaurant.setType(updateRestaurantDto.getType());

        return restaurantRepository.save(updatingRestaurant);
    }

    public void deleteRestaurant(UUID uuid) {
        restaurantRepository.deleteById(uuid);
    }
}
