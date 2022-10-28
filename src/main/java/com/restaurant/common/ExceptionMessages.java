package com.restaurant.common;

public final class ExceptionMessages {

    public static final String RESTAURANT_NOT_FOUND_EXCEPTION_MESSAGE =
            "Restaurant not found";
    public static final String AT_LEAST_ONE_PROPERTY_SHOULD_BE_PRESENT_EXCEPTION_MESSAGE =
            "At least one property should be present";
    public static final String MEAL_NOT_FOUND_EXCEPTION_MESSAGE =
            "Meal not found";
    public static final String MISSING_MEAL_IN_RESTAURANT_MENU_EXCEPTION_MESSAGE =
            "Meal must be present in restaurant menu";

    private ExceptionMessages() {
        throw new AssertionError("No MenuManagerExceptionMessages instances for you!");
    }
}
