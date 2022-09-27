package com.restaurant.controller;

import com.restaurant.model.Restaurant;
import com.restaurant.model.RestaurantType;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
class RestaurantControllerTest {

    @Autowired
    private RestaurantController restaurantController;

    @Test
    void should_create_restaurant() {
        Restaurant restaurant = Restaurant.builder().
                uuid(UUID.fromString("e3856e34-3e3f-11ed-b878-0242ac120002")).
                name("Ciastkarnia").
                address("Poznan").
                type(RestaurantType.AMERICAN).
                build();

        given().
                body(restaurant).
                contentType(ContentType.JSON).
                when().
                post("/restaurants").
                then().
                statusCode(200).
                body("uuid", equalTo("e3856e34-3e3f-11ed-b878-0242ac120002")).
                body("name", equalTo("Ciastkarnia")).
                body("address", equalTo("Poznan")).
                body("type", equalTo("AMERICAN"));
    }

    @Test
    void should_get_restaurants() {
        when().
                get("/restaurants").
                then().
                statusCode(200);
    }

    @Test
    void should_get_single_restaurant_by_id() {
        given().
            pathParam("uuid", UUID.fromString("6c545d39-f6e1-4731-b5c2-95a1338515ce")).
        when().
            get("/restaurants/{uuid}").
        then().
            statusCode(200).
            body("uuid",equalTo("6c545d39-f6e1-4731-b5c2-95a1338515ce")).
            body("name", equalTo("Restauracja2")).
            body("address", equalTo("Opole 2")).
            body("type", equalTo("ASIAN"));

    }

    /*@Test
    void should_update_restaurant() {
        given()
    }*/

}