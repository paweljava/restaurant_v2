package com.restaurant.util;

import java.util.Optional;

public class Optionality {

    public static <T> Optional<T> some(T value) {
        return Optional.of(value);
    }

    private Optionality() {
        throw new AssertionError("No Optionality instances for you!");
    }
}
