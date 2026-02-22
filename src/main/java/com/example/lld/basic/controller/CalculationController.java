package com.example.lld.basic.controller;

import com.example.lld.basic.dto.CalculationRequest;
import com.example.lld.basic.dto.CalculationResponse;
import com.example.lld.basic.entity.CalculationHistory;
import com.example.lld.basic.repository.CalculationHistoryRepository;
import com.example.lld.basic.service.CalculationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/calc")
public class CalculationController {

    private final CalculationService service;
    private final CalculationHistoryRepository repository;

    public CalculationController(CalculationService service,
                                 CalculationHistoryRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    // GET API: add
    // http://localhost:9000/api/calc/add?a=3&b=7
    @GetMapping("/add")
    public int add(@RequestParam int a, @RequestParam int b) {
        return service.add(a, b);
    }

    // POST API: multiply
    // http://localhost:9000/api/calc/multiply
    @PostMapping("/multiply")
    public int multiply(@RequestBody CalculationRequest request) {
        return service.multiply(request.getA(), request.getB());
    }

    // GET API: fetch calculation history
    // http://localhost:9000/api/calc/history
    @GetMapping("/history")
    public Page<CalculationResponse> getHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageRequest pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return repository.findAll(pageable)
                .map(h -> new CalculationResponse(
                        h.getOperation(),
                        h.getA(),
                        h.getB(),
                        h.getResult(),
                        h.getCreatedAt()
                ));
    }
}