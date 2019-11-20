package com.jke.jkeweb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jke.jkeweb.jpa.NewAccount;
import com.jke.jkeweb.util.LogUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Configuration
@Service
public class MQSender {

    @Value("${ibm.mq.queueName.req}")
    private String queueNameReq;

    @Value("${ibm.mq.queueName.res}")
    private String queueNameRes;

    @Value("${jke.mq.ace.enabled}")
    private boolean aceEnabled;

    @Resource
    private JmsTemplate jmsTemplate;

    public void send(NewAccount newAccount) {
        LogUtil.log("MQSender : send : Started ");

        try {
            final String jsonString = new ObjectMapper().writeValueAsString(newAccount);

            String queueName = null;
            if (aceEnabled) {
                queueName = queueNameReq;
            } else {
                queueName = queueNameRes;
            }

            LogUtil.log("MQSender : send aceEnabled  : " + aceEnabled);
            LogUtil.log("MQSender : send queueName  : " + queueName);
            LogUtil.logDebug("MQSender : send Msg  : " + jsonString);

            jmsTemplate.send(queueName, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(jsonString);
                }
            });

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}