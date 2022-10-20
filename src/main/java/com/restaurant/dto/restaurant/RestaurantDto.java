package com.restaurant.dto.restaurant;

import com.restaurant.dto.meal.MealDto;
import com.restaurant.model.RestaurantType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {

    private String name;
    private String address;
    @Enumerated(EnumType.STRING)
    private RestaurantType type;
    private List<MealDto> mealsDto;
}
