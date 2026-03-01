package com.example.ft.caloriestracking.repository;

import com.example.ft.caloriestracking.entity.DailyIntake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DailyIntakeRepository
        extends JpaRepository<DailyIntake, Long> {

    @Query(
            "select coalesce(sum(d.calories), 0) " +
                    "from DailyIntake d " +
                    "where d.userId = :userId and d.intakeDate = :date"
    )
    Double getTotalCalories(
            @Param("userId") Long userId,
            @Param("date") LocalDate date
    );

    @Query(
            "select coalesce(sum(d.protein), 0) " +
                    "from DailyIntake d " +
                    "where d.userId = :userId and d.intakeDate = :date"
    )
    Double getTotalProtein(
            @Param("userId") Long userId,
            @Param("date") LocalDate date
    );

    @Query(
            "select coalesce(sum(d.carbs), 0) " +
                    "from DailyIntake d " +
                    "where d.userId = :userId and d.intakeDate = :date"
    )
    Double getTotalCarbs(
            @Param("userId") Long userId,
            @Param("date") LocalDate date
    );

    @Query(
            "select coalesce(sum(d.fat), 0) " +
                    "from DailyIntake d " +
                    "where d.userId = :userId and d.intakeDate = :date"
    )
    Double getTotalFat(
            @Param("userId") Long userId,
            @Param("date") LocalDate date
    );

    List<DailyIntake> getByUserIdAndIntakeDate(
            @Param("userId") Long userId,
            @Param("date") LocalDate date
    );
}
