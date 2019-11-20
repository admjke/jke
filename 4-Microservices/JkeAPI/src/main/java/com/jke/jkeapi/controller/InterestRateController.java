package com.jke.jkeapi.controller;

import com.jke.jkeapi.jpa.InterestRate;
import com.jke.jkeapi.service.InterestRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class InterestRateController {

    @Autowired
    InterestRateService interestRateService;

    @GetMapping("/interestRates")
    private List<InterestRate> getAllInterestRates() {
        return interestRateService.getAllInterestRates();
    }

    @GetMapping("/interestRatesByCategory")
    private List<InterestRate> getAllInterestRates(@RequestParam("category") String category) {
        return interestRateService.getAllInterestRates(category);
    }

    @GetMapping("/interestRates/{id}")
    private InterestRate getInterestRate(@PathVariable("id") int id) {
        return interestRateService.getInterestRate(id);
    }

    @DeleteMapping("/interestRates/{id}")
    private void deleteInterestRate(@PathVariable("id") int id) {
        interestRateService.delete(id);
    }

    @PostMapping("/interestRates")
    private int saveInterestRate(@RequestBody InterestRate interestRate) {
        interestRateService.saveOrUpdate(interestRate);
        return interestRate.getId();
    }
}
