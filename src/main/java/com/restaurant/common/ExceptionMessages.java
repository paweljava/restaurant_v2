package com.restaurant.common;

public final class ExceptionMessages {
    public static final String RESTAURANT_NOT_FOUND_EXCEPTION_MESSAGE = "Restaurant not found";
    public static final String AT_LEAST_ONE_PROPERTY_SHOULD_BE_PRESENT_EXCEPTION_MESSAGE = "At least one property should be present";
    public static final String MEAL_NOT_FOUND_EXCEPTION_MESSAGE = "Meal not found";



    public static final String MISSING_MEAL_IN_RESTAURANT_MENU_EXCEPTION_MESSAGE = "Meal must be present in restaurant menu";
    public static final String MEAL_IS_ALREADY_PRESENT_IN_MENU_EXCEPTION_MESSAGE = "Meal is already present in restaurant menu";
    public static final String SET_MUST_HAVE_AT_LEAST_ONE_DISH_EXCEPTION_MESSAGE = "Set must have at least one dish";
    public static final String MENU_POSITION_HAVE_TO_BE_SET_EXCEPTION_MESSAGE = "Menu position have to be set";
    public static final String MENU_POSITION_HAVE_TO_BE_DISH_EXCEPTION_MESSAGE = "Added/removed menu position have to be dish";
    public static final String SET_GROUP_LIMITATION_EXCEEDED_EXCEPTION_MESSAGE = "Set should contain from 1 to 3 dish groups";
    public static final String MISSING_DISH_GROUP_EXCEPTION_MESSAGE = "Request should have dishGroup parameter";
    public static final String MISSING_DISH_ID_LIST_PARAMETER_EXCEPTION_MESSAGE = "Request should have dishIdList parameter";

    private ExceptionMessages() {
        throw new AssertionError("No MenuManagerExceptionMessages instances for you!");
    }
}
