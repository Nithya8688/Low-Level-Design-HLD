package com.example.ft.caloriestracking.service;


import com.example.ft.caloriestracking.dto.DayIntakeRequest;
import com.example.ft.caloriestracking.dto.DayIntakeResponse;
import com.example.ft.caloriestracking.dto.FoodItemRequest;
import com.example.ft.caloriestracking.entity.DailyIntake;
import com.example.ft.caloriestracking.entity.Food;
import com.example.ft.caloriestracking.repository.DailyIntakeRepository;
import com.example.ft.caloriestracking.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CalorieCalculationService {

    private final FoodRepository foodRepository;
    @Autowired
    private FoodRepository repository;
    @Autowired
    private DailyIntakeRepository intakeRepository;

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

    public List<Food> addFoods(List<Food> foods) {

        List<Food> foodsToSave = foods.stream()
                .filter(food -> repository.findByNameIgnoreCase(food.getName()).isEmpty())
                .toList();

        return repository.saveAll(foodsToSave);
    }

    public DayIntakeResponse getTotalCalories(DayIntakeRequest request) {

        List<DailyIntake> dailyIntakes =
                intakeRepository.getByUserIdAndIntakeDate(request.getUserId(),  LocalDate.parse(request.getDate()));

        double totalCalories = 0;
        double totalProtein = 0;
        double totalCarbs = 0;
        double totalFat = 0;

        for (DailyIntake intake : dailyIntakes) {
            totalCalories += intake.getCalories() != null ? intake.getCalories() : 0;
            totalProtein  += intake.getProtein()  != null ? intake.getProtein()  : 0;
            totalCarbs    += intake.getCarbs()    != null ? intake.getCarbs()    : 0;
            totalFat      += intake.getFat()      != null ? intake.getFat()      : 0;
        }

        DayIntakeResponse response = new DayIntakeResponse();
        response.setTotalCalories(totalCalories);
        response.setTotalProtein(totalProtein);
        response.setTotalCarbs(totalCarbs);
        response.setTotalFat(totalFat);

        return response;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}