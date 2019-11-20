package com.jke.JkeStock.controller;

import com.jke.JkeStock.entity.JkeStockQuote;
import com.jke.JkeStock.service.JkeStockQuoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/stock")
public class JkeStockQuoteController {

    public static final Logger logger = LoggerFactory.getLogger(JkeStockQuoteController.class);

    @Autowired
    JkeStockQuoteService service;

    @CrossOrigin
    @GetMapping("stocks")
    private List<JkeStockQuote> findAll() {
        return service.findAll();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    @ResponseBody
    private JkeStockQuote findOne (@PathVariable("id") int id) {
        return service.find(id);
    }

    @PostMapping
    @ResponseBody
    private JkeStockQuote create(@RequestBody JkeStockQuote entity) {
        return service.create(entity);
    }

    @CrossOrigin
    @PutMapping
    private JkeStockQuote update(@RequestBody JkeStockQuote entity) {
        return service.update(entity);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    private JkeStockQuote delete (@PathVariable("id") int id) {
        return service.delete(id);
    }

}
