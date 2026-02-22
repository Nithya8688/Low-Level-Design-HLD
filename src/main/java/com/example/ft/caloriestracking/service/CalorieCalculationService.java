package com.example.ft.caloriestracking.service;


import com.example.ft.caloriestracking.dto.DayIntakeRequest;
import com.example.ft.caloriestracking.dto.DayIntakeResponse;
import com.example.ft.caloriestracking.dto.FoodItemRequest;
import com.example.ft.caloriestracking.entity.Food;
import com.example.ft.caloriestracking.repository.FoodRepository;
import org.springframework.stereotype.Service;

@Service
public class CalorieCalculationService {

    private final FoodRepository foodRepository;

    public CalorieCalculationService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public DayIntakeResponse calculate(DayIntakeRequest request) {

        double calories = 0;
        double protein = 0;
        double carbs = 0;
        double fat = 0;

        for (FoodItemRequest item : request.getItems()) {

            Food food = foodRepository.findByNameIgnoreCase(item.getFood())
                    .orElseThrow(() ->
                            new RuntimeException("Food not found: " + item.getFood())
                    );

            double factor = item.getGrams() / 100.0;

            calories += food.getCaloriesPer100g() * factor;
            protein += food.getProteinPer100g() * factor;
            carbs += food.getCarbsPer100g() * factor;
            fat += food.getFatPer100g() * factor;
        }

        DayIntakeResponse response = new DayIntakeResponse();
        response.setDate(request.getDate());
        response.setTotalCalories(round(calories));
        response.setTotalProtein(round(protein));
        response.setTotalCarbs(round(carbs));
        response.setTotalFat(round(fat));

        return response;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}