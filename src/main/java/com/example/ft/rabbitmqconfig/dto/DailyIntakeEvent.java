package com.example.ft.rabbitmqconfig.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
public class DailyIntakeEvent implements Serializable {

    private Long userId;
    private String date;

    public DailyIntakeEvent() {}

    public DailyIntakeEvent(Long userId, String date) {
        this.userId = userId;
        this.date = date;
    }
}
