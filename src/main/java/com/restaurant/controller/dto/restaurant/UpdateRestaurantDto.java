package com.restaurant.controller.dto.restaurant;

import com.restaurant.model.RestaurantType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class UpdateRestaurantDto {

    private Optional<String> name = Optional.empty();
    private Optional<String> address = Optional.empty();
    @Enumerated(EnumType.STRING)
    private Optional<RestaurantType> type = Optional.empty();
}
