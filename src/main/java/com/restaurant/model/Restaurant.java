package com.restaurant.model;

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
    private UUID uuid;
    private String name;
    private String address;
    @Enumerated(EnumType.STRING)
    private RestaurantType type;
    @OneToMany(cascade = CascadeType.REMOVE)//(orphanRemoval = true)
    @JoinColumn(name = "restaurantUuid", updatable = false, insertable = false)
    private List<Meal> meals;


    public Restaurant(String name, String address, RestaurantType type) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.address = address;
        this.type = type;
    }
}
