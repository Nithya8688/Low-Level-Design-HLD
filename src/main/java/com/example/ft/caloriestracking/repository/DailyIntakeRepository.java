package com.example.ft.caloriestracking.repository;

import com.example.ft.caloriestracking.entity.DailyIntake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface DailyIntakeRepository
        extends JpaRepository<DailyIntake, Long> {
    @Query(
            "select coalesce(sum(d.calories), 0) " +
                    "from DailyIntake d " +
                    "where d.userId = :userId " +
                    "and d.intakeDate = :date"
    )
    Double getTotalCalories(
            @Param("userId") Long userId,
            @Param("date") LocalDate date
    );
}
