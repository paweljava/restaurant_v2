package com.restaurant.service;

import com.restaurant.common.ExceptionMessages;
import com.restaurant.controller.dto.restaurant.CreateRestaurantDto;
import com.restaurant.controller.dto.restaurant.UpdateRestaurantDto;
import com.restaurant.model.Restaurant;
import com.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.restaurant.util.Checks.checkThat;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant addRestaurant(CreateRestaurantDto createRestaurantDto) {
        final var restaurant = new Restaurant(
                createRestaurantDto.getName(),
                createRestaurantDto.getAddress(),
                createRestaurantDto.getType()
        );
        return restaurantRepository.save(restaurant);
    }

    @Cacheable(cacheNames = "allRestaurants")
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Cacheable(cacheNames = "restaurantById")
    public Restaurant getRestaurantById(UUID id) {
        return findRestaurant(id);
    }

    // immutable method
    public Restaurant updateRestaurant(UUID id, UpdateRestaurantDto request) {
        final var restaurant = findRestaurant(id);
        checkThat(atLeastOneFieldPresent(request),
                ExceptionMessages.AT_LEAST_ONE_PROPERTY_SHOULD_BE_PRESENT_EXCEPTION_MESSAGE);

        return restaurantRepository.save(new Restaurant(
                restaurant.getId(),
                request.getName().orElse(restaurant.getName()),
                request.getAddress().orElse(restaurant.getAddress()),
                request.getType().orElse(restaurant.getType()),
                restaurant.getMeals())
        );
    }
    //mutable method
    /*public Restaurant updateRestaurant(UUID id, UpdateRestaurantDto request) {
        final var restaurant = findRestaurant(id);
        Checks.checkThat(atLeastOneFieldPresent(request),
                ExceptionMessages.AT_LEAST_ONE_PROPERTY_SHOULD_BE_PRESENT_EXCEPTION_MESSAGE);

        restaurant.setId(restaurant.getId());
        request.getName().ifPresent(restaurant::setName);
        request.getAddress().ifPresent(restaurant::setAddress);
        request.getType().ifPresent(restaurant::setType);

        return restaurantRepository.save(restaurant);
    }*/

    // immutable method with ifPresentOrElse
    /*public Restaurant updateRestaurant2(UUID id, UpdateRestaurantDto request) {
        final var restaurant = findRestaurant(id);
        checkThat(atLeastOneFieldPresent(request),
                ExceptionMessages.AT_LEAST_ONE_PROPERTY_SHOULD_BE_PRESENT_EXCEPTION_MESSAGE);
        final Restaurant updatedRestaurant;
        updatedRestaurant = restaurant;

        updatedRestaurant.setId(restaurant.getId());
        request.getName().ifPresentOrElse(updatedRestaurant::setName,
                () -> updatedRestaurant.setName(restaurant.getName()));
        request.getAddress().ifPresentOrElse(updatedRestaurant::setAddress,
                () -> updatedRestaurant.setAddress(restaurant.getAddress()));
        request.getType().ifPresentOrElse(updatedRestaurant::setType,
                () -> updatedRestaurant.setType(restaurant.getType()));
        updatedRestaurant.setMeals(restaurant.getMeals());

        return restaurantRepository.save(updatedRestaurant);
    }*/

    public boolean deleteRestaurant(UUID id) {
        final var restaurant = findRestaurant(id);
        restaurantRepository.deleteById(restaurant.getId());
        return restaurantRepository.findById(id).isEmpty();
    }

    private Restaurant findRestaurant(UUID id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(ExceptionMessages.RESTAURANT_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    private boolean atLeastOneFieldPresent(UpdateRestaurantDto request) {
        return request.getName().isPresent() ||
                request.getAddress().isPresent() ||
                request.getType().isPresent();
    }
}
