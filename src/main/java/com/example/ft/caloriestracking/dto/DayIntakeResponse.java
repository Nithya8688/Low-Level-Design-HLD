package com.example.ft.caloriestracking.dto;

import lombok.Data;

@Data
public class DayIntakeResponse {

    private String date;
    private double totalCalories;
    private double totalProtein;
    private double totalCarbs;
    private double totalFat;

    // getters & setters
}
