package com.example.lld.basic.service;




import com.example.lld.basic.entity.CalculationHistory;
import com.example.lld.basic.repository.CalculationHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CalculationService {

    private final CalculationHistoryRepository repository;

    public CalculationService(CalculationHistoryRepository repository) {
        this.repository = repository;
    }

    // ADD operation
    public int add(int a, int b) {
        int result = a + b;
        saveHistory("ADD", a, b, result);
        return result;
    }

    // MULTIPLY operation
    public int multiply(int a, int b) {
        int result = a * b;
        saveHistory("MULTIPLY", a, b, result);
        return result;
    }

    // save calculation to DB
    private void saveHistory(String operation, int a, int b, int result) {
        CalculationHistory history = new CalculationHistory();
        history.setOperation(operation);
        history.setA(a);
        history.setB(b);
        history.setResult(result);
        repository.save(history);
    }
}

