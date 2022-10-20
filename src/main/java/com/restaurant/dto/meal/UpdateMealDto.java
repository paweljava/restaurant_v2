package com.restaurant.dto.meal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor

public final class UpdateMealDto {

    private Optional<String> name;
    private Optional<Float> price;
}
