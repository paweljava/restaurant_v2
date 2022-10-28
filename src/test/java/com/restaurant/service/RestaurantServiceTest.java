package com.restaurant.service;

import com.restaurant.controller.dto.restaurant.CreateRestaurantDto;
import com.restaurant.controller.dto.restaurant.UpdateRestaurantDto;
import com.restaurant.model.Meal;
import com.restaurant.model.Restaurant;
import com.restaurant.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.restaurant.model.RestaurantType.*;
import static com.restaurant.util.Optionality.some;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class RestaurantServiceTest {

    private final RestaurantRepository restaurantRepository = mock(RestaurantRepository.class);
    private final RestaurantService restaurantService = new RestaurantService(restaurantRepository);

    @Test
    void should_create_restaurants() {
        // given
        final var createRestaurantDto = new CreateRestaurantDto("Nazwa", "Adres", FRENCH);
        final var restaurant = new Restaurant(createRestaurantDto);

        given(restaurantRepository.save(any())).willReturn(restaurant);

        // when
        final var result = restaurantService.addRestaurant(createRestaurantDto);

        // then
        assertEquals(restaurant, result);
        assertEquals(restaurant.getName(), result.getName());
        assertEquals(restaurant.getAddress(), result.getAddress());
        assertEquals(restaurant.getType(), result.getType());
        verify(restaurantRepository).save(any());
        verify(restaurantRepository).save(argThat(
                r -> r.getName().equals(restaurant.getName())
                        && r.getAddress().equals(restaurant.getAddress())
                        && r.getType().equals((restaurant.getType())))
        );
    }

    @Test
    void should_get_restaurants() {
        // given
        final var restaurant1 = new Restaurant("Nazwa1", "Adres1", MEDITERRANEAN, List.of());
        final var restaurant2 = new Restaurant("Nazwa2", "Adres2", POLISH, List.of());
        final var restaurant3 = new Restaurant("Nazwa3", "Adres3", AMERICAN, List.of());
        final var restaurantList = List.of(restaurant1, restaurant2, restaurant3);

        given(restaurantRepository.findAll()).willReturn(restaurantList);

        // when
        final var result = restaurantService.getAllRestaurants();

        // then
        verify(restaurantRepository).findAll();
        assertFalse(result.isEmpty());
        assertEquals(3, restaurantService.getAllRestaurants().size());
        assertNotEquals(0, restaurantService.getAllRestaurants().size());
        assertTrue(restaurantService.getAllRestaurants().contains(restaurant1));
        assertTrue(restaurantService.getAllRestaurants().contains(restaurant2));
        assertTrue(restaurantService.getAllRestaurants().contains(restaurant3));
        assertEquals("Nazwa1", restaurantService.getAllRestaurants().get(0).getName());
        assertEquals("Adres2", restaurantService.getAllRestaurants().get(1).getAddress());
        assertEquals(AMERICAN, restaurantService.getAllRestaurants().get(2).getType());
    }

    @Test
    void should_get_restaurant_by_id() {
        // given
        List<Meal> meals = List.of(new Meal("Posilek", 23), new Meal("Posilek", 15));
        final var restaurant = new Restaurant("Nazwa", "Adres", POLISH, meals);

        given(restaurantRepository.findById(restaurant.getId())).willReturn(Optional.of(restaurant));

        // when
        final var result = restaurantService.getRestaurantById(restaurant.getId());

        // then
        assertEquals(restaurant.getName(), result.getName());
        assertEquals(restaurant.getAddress(), result.getAddress());
        assertEquals(restaurant.getType(), result.getType());
        verify(restaurantRepository).findById(restaurant.getId());
    }

    @Test
    void should_get_restaurant_by_id_without_meals() {
        // given
        final var restaurant = new Restaurant("Nazwa", "Adres", POLISH, List.of());

        given(restaurantRepository.findById(restaurant.getId())).willReturn(Optional.of(restaurant));

        // when
        final var result = restaurantService.getRestaurantById(restaurant.getId());

        // then
        assertEquals(restaurant.getName(), result.getName());
        assertEquals(restaurant.getAddress(), result.getAddress());
        assertEquals(restaurant.getType(), result.getType());
        verify(restaurantRepository).findById(restaurant.getId());
    }

    @Test
    void should_update_restaurant() {
        // given
        final var restaurant = new Restaurant("Nazwa", "Adres", POLISH);
        final var updateRestaurantDto = new UpdateRestaurantDto(some("Restauracja"), some("Konin"), some(AMERICAN));
        final var updatedRestaurant = new Restaurant(restaurant.getId(), "Restauracja", "Konin", AMERICAN);

        given(restaurantRepository.save(updatedRestaurant)).willReturn(updatedRestaurant);
        given(restaurantRepository.findById(restaurant.getId())).willReturn(Optional.of(restaurant));

        // when
        final var result = restaurantService.updateRestaurant(restaurant.getId(), updateRestaurantDto);

        // then
        assertEquals(restaurant.getId(), result.getId());
        assertEquals(updateRestaurantDto.getName().get(), result.getName());
        assertEquals(updateRestaurantDto.getAddress().get(), result.getAddress());
        assertEquals(updateRestaurantDto.getType().get(), result.getType());
        assertNotEquals(restaurant.getName(), updateRestaurantDto.getName().get());
        assertNotEquals(restaurant.getAddress(), updateRestaurantDto.getAddress().get());
        assertNotEquals(restaurant.getType(), updateRestaurantDto.getType().get());
        verify(restaurantRepository).save(result);
        verify(restaurantRepository).findById(result.getId());
    }

    @Test
    void should_update_only_restaurant_name() {
        // given
        final var restaurant = new Restaurant("Nazwa", "Adres", POLISH);
        final var updateRestaurantDto = new UpdateRestaurantDto(
                some("Restauracja"),
                Optional.empty(),
                Optional.empty());
        final var updatedRestaurant = new Restaurant(restaurant.getId(), "Restauracja", "Adres", POLISH);

        given(restaurantRepository.save(updatedRestaurant)).willReturn(updatedRestaurant);
        given(restaurantRepository.findById(restaurant.getId())).willReturn(Optional.of(restaurant));

        // when
        final var result = restaurantService.updateRestaurant(restaurant.getId(), updateRestaurantDto);

        // then
        assertEquals(restaurant.getId(), result.getId());
        assertEquals(updateRestaurantDto.getName().get(), result.getName());
        assertEquals(restaurant.getAddress(), result.getAddress());
        assertEquals(restaurant.getType(), result.getType());
        assertNotEquals(restaurant.getName(), updateRestaurantDto.getName().get());
        verify(restaurantRepository).save(result);
        verify(restaurantRepository).findById(result.getId());
    }

    @Test
    void should_update_only_restaurant_address() {
        // given
        final var restaurant = new Restaurant("Nazwa", "Adres", POLISH);
        final var updateRestaurantDto = new UpdateRestaurantDto(Optional.empty(), some("Konin"), Optional.empty());
        final var updatedRestaurant = new Restaurant(restaurant.getId(), "Nazwa", "Konin", POLISH);

        given(restaurantRepository.save(updatedRestaurant)).willReturn(updatedRestaurant);
        given(restaurantRepository.findById(restaurant.getId())).willReturn(Optional.of(restaurant));

        // when
        final var result = restaurantService.updateRestaurant(restaurant.getId(), updateRestaurantDto);

        // then
        assertEquals(restaurant.getId(), result.getId());
        assertEquals(restaurant.getName(), result.getName());
        assertEquals(updateRestaurantDto.getAddress().get(), result.getAddress());
        assertEquals(restaurant.getType(), result.getType());
        assertNotEquals(restaurant.getAddress(), updateRestaurantDto.getAddress().get());
        verify(restaurantRepository).save(result);
        verify(restaurantRepository).findById(result.getId());
    }

    @Test
    void should_update_only_restaurant_type() {
        // given
        final var restaurant = new Restaurant("Nazwa", "Adres", POLISH);
        final var updateRestaurantDto = new UpdateRestaurantDto(Optional.empty(), Optional.empty(), some(AMERICAN));
        final var updatedRestaurant = new Restaurant(restaurant.getId(), "Nazwa", "Adres", AMERICAN);

        given(restaurantRepository.save(updatedRestaurant)).willReturn(updatedRestaurant);
        given(restaurantRepository.findById(restaurant.getId())).willReturn(Optional.of(restaurant));

        // when
        final var result = restaurantService.updateRestaurant(restaurant.getId(), updateRestaurantDto);

        // then
        assertEquals(restaurant.getId(), result.getId());
        assertEquals(restaurant.getName(), result.getName());
        assertEquals(restaurant.getAddress(), result.getAddress());
        assertEquals(updateRestaurantDto.getType().get(), result.getType());
        assertNotEquals(restaurant.getType(), updateRestaurantDto.getType().get());
        verify(restaurantRepository).save(result);
        verify(restaurantRepository).findById(result.getId());
    }

    @Test
    void should_not_update_restaurant_name() {
        // given
        final var restaurant = new Restaurant("Nazwa", "Adres", POLISH);
        final var updateRestaurantDto = new UpdateRestaurantDto(Optional.empty(), some("Konin"), some(AMERICAN));
        final var updatedRestaurant = new Restaurant(restaurant.getId(), "Nazwa", "Konin", AMERICAN);

        given(restaurantRepository.save(updatedRestaurant)).willReturn(updatedRestaurant);
        given(restaurantRepository.findById(restaurant.getId())).willReturn(Optional.of(restaurant));

        // when
        final var result = restaurantService.updateRestaurant(restaurant.getId(), updateRestaurantDto);

        // then
        assertEquals(restaurant.getId(), result.getId());
        assertEquals(restaurant.getName(), result.getName());
        assertEquals(updateRestaurantDto.getAddress().get(), result.getAddress());
        assertEquals(updateRestaurantDto.getType().get(), result.getType());
        assertNotEquals(restaurant.getAddress(), updateRestaurantDto.getAddress().get());
        assertNotEquals(restaurant.getType(), updateRestaurantDto.getType().get());
        verify(restaurantRepository).save(result);
        verify(restaurantRepository).findById(result.getId());
    }

    @Test
    void should_not_update_restaurant_address() {
        // given
        final var restaurant = new Restaurant("Nazwa", "Adres", POLISH);
        final var updateRestaurantDto = new UpdateRestaurantDto(some("Restauracja"), Optional.empty(), some(AMERICAN));
        final var updatedRestaurant = new Restaurant(restaurant.getId(), "Restauracja", "Adres", AMERICAN);

        given(restaurantRepository.save(updatedRestaurant)).willReturn(updatedRestaurant);
        given(restaurantRepository.findById(restaurant.getId())).willReturn(Optional.of(restaurant));

        // when
        final var result = restaurantService.updateRestaurant(restaurant.getId(), updateRestaurantDto);

        // then
        assertEquals(restaurant.getId(), result.getId());
        assertEquals(updateRestaurantDto.getName().get(), result.getName());
        assertEquals(restaurant.getAddress(), result.getAddress());
        assertEquals(updateRestaurantDto.getType().get(), result.getType());
        assertNotEquals(restaurant.getName(), updateRestaurantDto.getName().get());
        assertNotEquals(restaurant.getType(), updateRestaurantDto.getType().get());
        verify(restaurantRepository).save(result);
        verify(restaurantRepository).findById(result.getId());
    }

    @Test
    void should_not_update_only_restaurant_type() {
        // given
        final var restaurant = new Restaurant("Nazwa", "Adres", POLISH);
        final var updateRestaurantDto = new UpdateRestaurantDto(some("Restauracja"), some("Konin"), Optional.empty());
        final var updatedRestaurant = new Restaurant(restaurant.getId(), "Restauracja", "Konin", POLISH);

        given(restaurantRepository.save(updatedRestaurant)).willReturn(updatedRestaurant);
        given(restaurantRepository.findById(restaurant.getId())).willReturn(Optional.of(restaurant));

        // when
        final var result = restaurantService.updateRestaurant(restaurant.getId(), updateRestaurantDto);

        // then
        assertEquals(restaurant.getId(), result.getId());
        assertEquals(updateRestaurantDto.getName().get(), result.getName());
        assertEquals(updateRestaurantDto.getAddress().get(), result.getAddress());
        assertEquals(restaurant.getType(), result.getType());
        assertNotEquals(restaurant.getName(), updateRestaurantDto.getName().get());
        assertNotEquals(restaurant.getAddress(), updateRestaurantDto.getAddress().get());
        verify(restaurantRepository).save(result);
        verify(restaurantRepository).findById(result.getId());
    }

    // jak zamockowac metode restaurantRepository.deleteById ?
    @Test
    void should_delete_restaurant() {
        // given
        final var restaurant = new Restaurant("Nazwa", "Adres", POLISH);

        given(restaurantRepository.findById(restaurant.getId())).
                willReturn(Optional.of(restaurant)).
                willReturn(Optional.empty());

        // when
        final var result = restaurantService.deleteRestaurant(restaurant.getId());

        // then
        assertTrue(result);
        verify(restaurantRepository).deleteById(restaurant.getId());
    }
}
