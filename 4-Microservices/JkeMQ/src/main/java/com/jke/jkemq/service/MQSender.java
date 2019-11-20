package com.jke.jkemq.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jke.jkemq.jpa.NewAccount;

import com.jke.jkemq.util.LogUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Configuration
@Service
public class MQSender {

    @Value("${ibm.mq.queueName.req}")
    private String queueNameReq;

    @Value("${ibm.mq.queueName.int}")
    private String queueNameInt;

    @Resource
    private JmsTemplate jmsTemplate;

    //Sends the NewAccount message in JSON format in REQ queue.
    //This method is dummy method, to test the flow
    public void sendReq(NewAccount newAccount) {
        LogUtil.log("MQSender : sendReq : Message to send -> newAccount object --->>>>: "+ newAccount);

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(newAccount);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        LogUtil.log("MQSender : sendReq : newAccount converted to jsonString --->>>>: "+ jsonString);

        this.jmsTemplate.convertAndSend(queueNameReq, jsonString);

        LogUtil.log("MQSender : sendReq : completed ");

    }

    //Sends the XML message in Int MQ
    //This method is dummy method, can be removed when ACE is ready
    public void sendInt(String accountInfoXmlString) {
        LogUtil.log("MQSender : sendInt : started --->>>>: "+ accountInfoXmlString);

        this.jmsTemplate.convertAndSend(queueNameInt, accountInfoXmlString);
        LogUtil.log("MQSender : sendInt : completed ");
    }

}