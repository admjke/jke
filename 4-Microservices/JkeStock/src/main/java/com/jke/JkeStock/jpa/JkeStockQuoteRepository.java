package com.jke.JkeStock.jpa;

import com.jke.JkeStock.entity.JkeStockQuote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JkeStockQuoteRepository extends CrudRepository<JkeStockQuote, Integer> {

}

