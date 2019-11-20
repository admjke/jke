package com.jke.jkeweb.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jke.jkeweb.common.LogUtil;
import com.jke.jkeweb.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

    @Value("${jke.service.url.stock.all}")
    private String url_all;

    @Autowired
    RestTemplate restTemplate;

    public List<Stock>   getStock() {
        return getStock(url_all);
    }

    public List<Stock> getStock(String url) {

        LogUtil.log("StockService  getStock Started : url " + url);

        List<Stock> result = new ArrayList<>();
        try {
            String jsonString = restTemplate.getForObject(url, String.class);

            ObjectMapper jsonMapper = new ObjectMapper();
            Stock[] values =  jsonMapper.readValue(jsonString, Stock[].class);

            if (values != null) {
                for (Stock stock : values) {
                    result.add(stock);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        LogUtil.log("StockService  getStock completed ");

        return result;
    }
}
