package com.example.ft.caloriestracking.controller;

import com.example.ft.caloriestracking.entity.Food;
import com.example.ft.caloriestracking.repository.FoodRepository;
import com.example.ft.caloriestracking.service.CalorieCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/foods")
public class FoodController {

    private final FoodRepository repository;

    @Autowired
    private CalorieCalculationService calorieCalculationService;

    public FoodController(FoodRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/add")
    public List<Food> addFoods(@RequestBody List<Food> foods) {
        return calorieCalculationService.addFoods(foods);
    }

    @GetMapping("/list")
    public List<Food> getAllFoods() {
        return repository.findAll();
    }
}
