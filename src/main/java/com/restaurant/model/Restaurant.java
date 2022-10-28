package com.restaurant.model;

import com.restaurant.controller.dto.restaurant.CreateRestaurantDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Restaurant {

    @Id
    private UUID id;
    private String name;
    private String address;
    @Enumerated(EnumType.STRING)
    private RestaurantType type;
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)//(orphanRemoval = true)
    @JoinColumn(name = "restaurantId", updatable = false, insertable = false)
    private List<Meal> meals;

    public Restaurant(String name, String address, RestaurantType type) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.address = address;
        this.type = type;
    }

    public Restaurant(UUID id, String name, String address, RestaurantType type) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.type = type;
    }

    public Restaurant(String name, String address, RestaurantType type, List<Meal> meals) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.address = address;
        this.type = type;
        this.meals = meals;
    }

    public Restaurant(CreateRestaurantDto createRestaurantDto) {
        this.id = UUID.randomUUID();
        this.name = createRestaurantDto.getName();
        this.address = createRestaurantDto.getAddress();
        this.type = createRestaurantDto.getType();
    }
}
