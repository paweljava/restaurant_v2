package com.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RestaurantApp {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantApp.class, args);
    }
}
