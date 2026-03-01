package com.example.ft.caloriestracking.service;

import com.example.ft.caloriestracking.dto.DayIntakeRequest;
import com.example.ft.caloriestracking.dto.FoodItemRequest;
import com.example.ft.caloriestracking.entity.DailyIntake;
import com.example.ft.caloriestracking.entity.Food;
import com.example.ft.caloriestracking.repository.DailyIntakeRepository;
import com.example.ft.caloriestracking.repository.FoodRepository;
import com.example.ft.kafkaconfig.producer.KafkaProducerService;
import com.example.ft.rabbitmqconfig.RabbitConfig;
import com.example.ft.rabbitmqconfig.dto.DailyIntakeEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DailyIntakeService {

    private final FoodRepository foodRepository;
    private final DailyIntakeRepository intakeRepository;
    private final RabbitTemplate rabbitTemplate;
    private final KafkaProducerService producerService;


    public DailyIntakeService(
            FoodRepository foodRepository,
            DailyIntakeRepository intakeRepository,
            RabbitTemplate rabbitTemplate,
            KafkaProducerService producerService) {

        this.foodRepository = foodRepository;
        this.intakeRepository = intakeRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.producerService = producerService;
    }

    public void saveDailyIntake(DayIntakeRequest request) {

        for (FoodItemRequest item : request.getItems()) {

            Food food = foodRepository.findByNameIgnoreCase(item.getFood())
                    .orElseThrow(() ->
                            new RuntimeException("Food not found: " + item.getFood()));

            double factor = item.getGrams() / 100.0;

            DailyIntake intake = new DailyIntake();
            intake.setUserId(request.getUserId());
            intake.setFoodName(food.getName());
            intake.setGrams(item.getGrams());
            intake.setCalories(food.getCaloriesPer100g() * factor);
            intake.setProtein(food.getProteinPer100g() * factor);
            intake.setCarbs(food.getCarbsPer100g() * factor);
            intake.setFat(food.getFatPer100g() * factor);
            intake.setIntakeDate(LocalDate.parse(request.getDate()));

            intakeRepository.save(intake);
        }

        // 🔥 Publish RabbitMQ Event
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.ROUTING_KEY,
                new DailyIntakeEvent(
                        request.getUserId(),
                        request.getDate()
                )
        );
        producerService.sendMessage(request.getUserId()+" "+request.getDate());
    }
}

