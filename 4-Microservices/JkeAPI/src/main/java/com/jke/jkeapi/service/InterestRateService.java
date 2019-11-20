package com.jke.jkeapi.service;

import com.jke.jkeapi.jpa.InterestRate;
import com.jke.jkeapi.jpa.InterestRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InterestRateService {

    @Autowired
    InterestRateRepository interestRateRepository;

    public List<InterestRate> getAllInterestRates() {
        List<InterestRate> interestRates = new ArrayList();
        interestRateRepository.findAll().forEach(interestRate -> interestRates.add(interestRate));
        return interestRates;
    }

    public List<InterestRate> getAllInterestRates(String category) {
        List<InterestRate> interestRates = new ArrayList();
        interestRateRepository.findByCategory(category).forEach(interestRate -> interestRates.add(interestRate));
        return interestRates;
    }

    public InterestRate getInterestRate(int id) {
        return interestRateRepository.findById(id).get();
    }

    public void saveOrUpdate(InterestRate interestRate) {
        interestRateRepository.save(interestRate);
    }

    public void delete(int id) {
        //delete interestRates
        interestRateRepository.deleteById(id);
    }
    
}
