package com.example.ft.caloriestracking.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_target")
public class UserTarget {

    @Id
    private Long userId;

    private Double dailyCalorieTarget;

    // getters & setters
}

