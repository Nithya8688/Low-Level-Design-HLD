package com.example.ft.caloriestracking.controller;
import com.example.ft.caloriestracking.dto.DayIntakeRequest;
import com.example.ft.caloriestracking.service.DailyIntakeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/daily-intake")
public class DailyIntakeController {

    private final DailyIntakeService service;

    public DailyIntakeController(DailyIntakeService service) {
        this.service = service;
    }

    @PostMapping
    public String logDailyIntake(@RequestBody DayIntakeRequest request) {
        service.saveDailyIntake(request);
        return "Daily intake saved successfully";
    }
}
