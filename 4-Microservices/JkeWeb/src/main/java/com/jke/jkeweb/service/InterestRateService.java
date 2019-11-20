package com.jke.jkeweb.service;

import com.jke.jkeweb.common.LogUtil;
import com.jke.jkeweb.jpa.InterestRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@Service
@Configuration
public class InterestRateService {

    @Value("${jke.service.url.interest.all}")
    private String url_all;

    @Autowired
    RestTemplate restTemplate;

    public List<InterestRate>   getInterestRate() {
        return getInterestRate(url_all);
    }

    public List<InterestRate> getInterestRate(String url) {
        LogUtil.log(("InterestRateService  getInterestRate Started : url " + url));

        List<InterestRate> result = new ArrayList<>();
        try {
            String jsonString = restTemplate.getForObject(url, String.class);

            ObjectMapper jsonMapper = new ObjectMapper();
            InterestRate[] values =  jsonMapper.readValue(jsonString, InterestRate[].class);

            if (values != null) {
                for (InterestRate interestRate : values) {
                    result.add(interestRate);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        LogUtil.log("InterestRateService  getInterestRate Completed");

        return result;
    }
}
