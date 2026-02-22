package com.example.ft.caloriestracking.controller;

import com.example.ft.rabbitmqconfig.Producer.DailyIntakeProducer;
import com.example.ft.rabbitmqconfig.dto.DailyIntakeEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class RabbitTestController {

    private final DailyIntakeProducer producer;

    public RabbitTestController(DailyIntakeProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/publish")
    public String publish() {
        producer.send(new DailyIntakeEvent(1L, "2026-02-21"));
        return "Event published";
    }
}
