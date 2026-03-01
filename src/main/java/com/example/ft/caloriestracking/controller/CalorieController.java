package com.example.ft.caloriestracking.controller;
import com.example.ft.caloriestracking.dto.DayIntakeRequest;
import com.example.ft.caloriestracking.dto.DayIntakeResponse;
import com.example.ft.caloriestracking.service.CalorieCalculationService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
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

    @PostMapping("/totalCalories")
    public ResponseEntity<Object> getTotalCalories(
            @RequestBody DayIntakeRequest request) {

        if (!StringUtils.hasText(request.getDate())) {
            return ResponseEntity
                    .badRequest()
                    .body("date is mandatory");
        }

        if (request.getUserId() == null) {
            return ResponseEntity
                    .badRequest()
                    .body("userId is mandatory");
        }

        return ResponseEntity.ok(service.getTotalCalories(request));
    }
}
