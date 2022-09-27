package com.restaurant.service;

import com.restaurant.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MealService {

    private final MealRepository mealRespository;

    @Autowired
    public MealService(MealRepository mealRespository) {
        this.mealRespository = mealRespository;
    }


}
