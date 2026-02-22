package com.example.ft.caloriestracking.repository;

import com.example.ft.caloriestracking.entity.UserTarget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTargetRepository
        extends JpaRepository<UserTarget, Long> {
}
