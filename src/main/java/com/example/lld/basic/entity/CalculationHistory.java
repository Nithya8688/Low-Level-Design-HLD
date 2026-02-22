package com.example.lld.basic.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CALCULATION_HISTORY")
public class CalculationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // primary key

    @Column(name = "OPERATION", nullable = false)
    private String operation; // ADD / MULTIPLY

    @Column(name = "A_VALUE", nullable = false)
    private Integer a; // first number

    @Column(name = "B_VALUE", nullable = false)
    private Integer b; // second number

    @Column(name = "RESULT", nullable = false)
    private Integer result; // result

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // getters and setters
    public Long getId() { return id; }
    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }
    public Integer getA() { return a; }
    public void setA(Integer a) { this.a = a; }
    public Integer getB() { return b; }
    public void setB(Integer b) { this.b = b; }
    public Integer getResult() { return result; }
    public void setResult(Integer result) { this.result = result; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}

