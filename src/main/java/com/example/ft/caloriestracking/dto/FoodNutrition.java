package com.example.ft.caloriestracking.dto;

import lombok.Data;

@Data
public class FoodNutrition {

    private String name;
    private double caloriesPer100g;
    private double proteinPer100g;
    private double carbsPer100g;
    private double fatPer100g;

    // constructor, getters
}
