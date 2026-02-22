package com.example.ft.caloriestracking.repository;


import com.example.ft.caloriestracking.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {

    Optional<Food> findByNameIgnoreCase(String name);
    Optional<Food> findByName(String name);
}