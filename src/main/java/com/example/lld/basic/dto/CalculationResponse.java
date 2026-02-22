package com.example.lld.basic.dto;

import java.time.LocalDateTime;

public class CalculationResponse {

    private String operation;
    private Integer a;
    private Integer b;
    private Integer result;
    private LocalDateTime createdAt;

    public CalculationResponse(String operation, Integer a, Integer b,
                               Integer result, LocalDateTime createdAt) {
        this.operation = operation;
        this.a = a;
        this.b = b;
        this.result = result;
        this.createdAt = createdAt;
    }

    public String getOperation() { return operation; }
    public Integer getA() { return a; }
    public Integer getB() { return b; }
    public Integer getResult() { return result; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
