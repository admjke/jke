package com.jke.JkeInterest.jpa;

import com.jke.JkeInterest.entity.InterestRate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InterestRateRepository extends CrudRepository<InterestRate, Integer> {

    List<InterestRate> findByCategory(String category);

}

