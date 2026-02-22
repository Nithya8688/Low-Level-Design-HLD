package com.example.lld.basic.repository;

import com.example.lld.basic.entity.CalculationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface CalculationHistoryRepository
        extends JpaRepository<CalculationHistory, Long> {
    List<CalculationHistory> findAllByOrderByCreatedAtDesc();
    Page<CalculationHistory> findAll(Pageable pageable);
}

