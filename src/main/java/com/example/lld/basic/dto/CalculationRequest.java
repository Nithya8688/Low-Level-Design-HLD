package com.example.lld.basic.dto;


import org.antlr.v4.runtime.misc.NotNull;

public class CalculationRequest {

    @NotNull
    private Integer a;

    @NotNull
    private Integer b;

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }
}