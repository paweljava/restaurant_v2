package com.restaurant.model;

import lombok.AllArgsConstructor;
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
    private UUID uuid;
    private UUID restaurantUuid;
    private String name;
    private float price;


    /*public Meal(UUID uuid, String name, float price) {
        this.uuid = uuid;
        this.name = name;
        this.price = price;
    }*/
}
