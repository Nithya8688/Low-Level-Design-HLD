package com.example.ft.caloriestracking.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
@Table(name = "daily_intake")
public class DailyIntake {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String foodName;

    private Double grams;

    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fat;

    private LocalDate intakeDate;

    // getters & setters
}
