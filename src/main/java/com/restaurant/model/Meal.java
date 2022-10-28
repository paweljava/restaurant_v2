package com.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Meal {

    @Id
    private UUID id;
    private UUID restaurantId;
    private String name;
    private Float price;

    public Meal(String name, float price) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
    }

    public Meal(UUID restaurantId, String name, float price) {
        this.id = UUID.randomUUID();
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
    }
}
