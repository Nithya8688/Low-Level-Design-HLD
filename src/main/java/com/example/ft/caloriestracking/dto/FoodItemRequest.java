package com.example.ft.caloriestracking.dto;

import lombok.Data;

@Data
public class FoodItemRequest {
    private String food;
    private double grams;

    // getters & setters
}