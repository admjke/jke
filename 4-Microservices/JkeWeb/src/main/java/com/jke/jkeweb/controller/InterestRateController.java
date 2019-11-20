package com.jke.jkeweb.controller;

import com.jke.jkeweb.jpa.InterestRate;
import com.jke.jkeweb.service.InterestRateService;
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
        return interestRateService.getInterestRate();
    }

}
