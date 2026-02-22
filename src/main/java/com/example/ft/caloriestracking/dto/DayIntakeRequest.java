package com.example.ft.caloriestracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DayIntakeRequest {
    private Long userId;
    private String date;
    private List<FoodItemRequest> items;

    // getters & setters
}
