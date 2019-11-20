package com.jke.jkeweb.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository /// here is the trick
public interface InterestRateRepository extends CrudRepository<InterestRate, Long> {

    List<InterestRate> findByCategory(String category);

}

