package com.restaurant.controller.dto.meal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class UpdateMealDto {

    private Optional<String> name = Optional.empty();
    private Optional<Float> price = Optional.empty();
}
