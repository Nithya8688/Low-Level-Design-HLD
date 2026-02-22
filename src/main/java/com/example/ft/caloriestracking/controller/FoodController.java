package com.example.ft.caloriestracking.controller;

import com.example.ft.caloriestracking.entity.Food;
import com.example.ft.caloriestracking.repository.FoodRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foods")
public class FoodController {

    private final FoodRepository repository;

    public FoodController(FoodRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/add")
    public List<Food> addFoods(@RequestBody List<Food> foods) {
        return repository.saveAll(foods);
    }

    @GetMapping("/list")
    public List<Food> getAllFoods() {
        return repository.findAll();
    }
}
