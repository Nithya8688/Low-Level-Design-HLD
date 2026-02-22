package com.example.ft.caloriestracking.controller;

import com.example.ft.caloriestracking.entity.UserTarget;
import com.example.ft.caloriestracking.repository.UserTargetRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/targets")
public class UserTargetController {

    private final UserTargetRepository repository;

    public UserTargetController(UserTargetRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public UserTarget setTarget(@RequestBody UserTarget target) {
        return repository.save(target);
    }
}

