package com.restaurant;

import com.restaurant.controller.dto.meal.CreateMealDto;
import com.restaurant.controller.dto.meal.UpdateMealDto;
import com.restaurant.controller.dto.restaurant.CreateRestaurantDto;
import com.restaurant.model.Meal;
import com.restaurant.model.Restaurant;
import com.restaurant.repository.MealRepository;
import com.restaurant.repository.RestaurantRepository;
import com.restaurant.service.RestaurantService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.restaurant.model.RestaurantType.*;
import static com.restaurant.util.Optionality.some;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
class RestaurantIntegrationTestAssured {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MealRepository mealRepository;

    private Restaurant restaurant;
    private Meal meal;

    @BeforeEach
    public void beforeEach() {
        restaurant = new Restaurant(
                "Burgerlandia",
                "Inorocław",
                FRENCH);

        restaurantRepository.save(restaurant);
        meal = new Meal(restaurant.getId(), "Pierogi", 22.78f);
        mealRepository.save(meal);
    }

    @Test
    void should_create_restaurant() {
        final var restaurant = new CreateRestaurantDto("Ciastkarnia", "Matrix", AMERICAN);
        given().
                body(restaurant).
                contentType(ContentType.JSON).
        when().
                post("/restaurants").
        then().
                statusCode(200).
                body("name", equalTo(restaurant.getName())).
                body("address", equalTo(restaurant.getAddress())).
                body("type", equalTo(restaurant.getType().toString()));
    }

    @Test
    void should_get_restaurants() {
        when().
                get("/restaurants").
        then().
                statusCode(200);
    }

    @Test
    void should_get_restaurant_by_id() {
        given().
                pathParam("id", restaurant.getId()).
                contentType(ContentType.JSON).
        when().
                get("/restaurants/{id}").
        then().
                statusCode(200).
                body("name", equalTo(restaurant.getName())).
                body("address", equalTo(restaurant.getAddress())).
                body("type", equalTo(restaurant.getType().toString()));
    }

    @Test
    void should_update_restaurant() {
        final var body = Restaurant.builder().
                name("FastFood").
                address("Poznan").
                type(MEDITERRANEAN).
                build();
        given().
                pathParam("id", restaurant.getId()).
                body(body).
                contentType(ContentType.JSON).
        when().
                put("/restaurants/{id}").
        then().
                statusCode(200).
                body("name", equalTo(body.getName())).
                body("address", equalTo(body.getAddress())).
                body("type", equalTo(body.getType().toString()));
    }

    @Test
    void should_delete_restaurant() {
        given().
                pathParam("id", restaurant.getId()).
        when().
                delete("/restaurants/{id}").
        then().
                statusCode(200);
    }

    @Test
    void should_create_meal() {
        final var body = new CreateMealDto("Pulpety", (float)21.10);
        given().
                pathParam("id", restaurant.getId()).
                body(body).
                contentType(ContentType.JSON).
        when().
                post("/restaurants/{id}/meals").
        then().
                statusCode(200).
                body("name", equalTo(body.getName())).
                body("price", equalTo(body.getPrice()));
    }

    @Test
    void should_update_meal() {
        final var body = new UpdateMealDto(some("Homar"), some((float)34.7));
        given().
                pathParam("id", restaurant.getId()).
                pathParam("mealid", meal.getId()).
                body(body).
                contentType(ContentType.JSON).
        when().
                put("/restaurants/{id}/meals/{mealid}").
        then().
                statusCode(200).
                body("name", equalTo(body.getName().get())).
                body("price", equalTo(body.getPrice().get()));
    }

    @Test
    void should_delete_meal() {
        given().
                pathParam("id", restaurant.getId()).
                pathParam("mealid", meal.getId()).
                contentType(ContentType.JSON).
        when().
                delete("/restaurants/{id}/meals/{mealid}").
        then().
                statusCode(200);
    }
}