package com.jke.JkeInterest.controller;

import com.jke.JkeInterest.entity.InterestRate;
import com.jke.JkeInterest.service.InterestRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/interest")
public class InterestRateController {

    @Autowired
    InterestRateService service;

    @GetMapping("/interestRates")
    private List<InterestRate> getAllInterestRates() {
        return service.getAllInterestRates();
    }

    @GetMapping("/interestRatesByCategory")
    private List<InterestRate> getAllInterestRates(@RequestParam("category") String category) {
        return service.getAllInterestRates(category);
    }

    @GetMapping("/interestRates/{id}")
    private InterestRate getInterestRate(@PathVariable("id") int id) {
        return service.getInterestRate(id);
    }

    @DeleteMapping("/interestRates/{id}")
    private void deleteInterestRate(@PathVariable("id") int id) {
        service.delete(id);
    }

    @PostMapping("/interestRates")
    private int saveInterestRate(@RequestBody InterestRate interestRate) {
        service.saveOrUpdate(interestRate);
        return interestRate.getId();
    }
}
