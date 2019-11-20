package com.jke.jkeweb.controller;

import com.jke.jkeweb.model.Stock;
import com.jke.jkeweb.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class StockController {

    @Autowired
    StockService stockService;

    @GetMapping("/stock")
    private List<Stock> getAllStock() { return stockService.getStock(); }
}
