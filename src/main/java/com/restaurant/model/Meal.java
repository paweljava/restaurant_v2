package com.restaurant.model;

import com.restaurant.dto.meal.CreateMealDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    public Meal(CreateMealDto createMealDto) {
        this.name = name;
        this.price = price;
    }
}
