package com.example.ft.caloriestracking.controller;
import com.example.ft.caloriestracking.dto.DayIntakeRequest;
import com.example.ft.caloriestracking.dto.DayIntakeResponse;
import com.example.ft.caloriestracking.service.CalorieCalculationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calories")
public class CalorieController {

    private final CalorieCalculationService service;

    public CalorieController(CalorieCalculationService service) {
        this.service = service;
    }

    @PostMapping("/calculate")
    public DayIntakeResponse calculate(@RequestBody DayIntakeRequest request) {
        return service.calculate(request);
    }
}
