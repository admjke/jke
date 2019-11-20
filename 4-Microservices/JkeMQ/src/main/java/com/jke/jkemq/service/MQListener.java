package com.jke.jkemq.service;

import com.jke.jkemq.jpa.NewAccount;
import com.jke.jkemq.util.ConversionUtil;
import com.jke.jkemq.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class MQListener {

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    MQSender mQSender;

//    // Listen to REQ queue
//    // Receives NewAccount Request in JSON format
//    // Convert the JSON format into XML format
//    // Put it into INT Queue
//    // Note: this method is for timebeing, it can be removed when ACE is ready.
//    @JmsListener(destination = "${ibm.mq.queueName.req}")
//    public void receiveReq(final Message jsonMessage)  {
//        LogUtil.log("MQListener : receiveReq: started  ");
//
//        String xmlString = ConversionUtil.jmsMessageInJsonToXml(jsonMessage);
//        //Create new account
//        if (xmlString != null) {
//            mQSender.sendInt(xmlString);
//        }
//
//        LogUtil.log("MQListener : receiveReq: completed  ");
//    }


    // Listen to INT queue
    // Receives NewAccount Request in XML format from ACE
    // Process it (create account)
    @JmsListener(destination = "${ibm.mq.queueName.int}")
    public void receiveInt(final Message xmlMessage) {
        LogUtil.log("MQListener : receiveInt: started  ");

        String jsonString = ConversionUtil.jmsMessageInXmlToJson(xmlMessage);
        //Create new account
        if (jsonString != null) {
            processCreateAccount(jsonString);
        }

        LogUtil.log("MQListener : receiveInt: completed  ");
    }


    // Listen to RES queue
    // Receives NewAccount Request in JSON format
    // Process it (create account)
    @JmsListener(destination = "${ibm.mq.queueName.res}")
    public void receiveRes(final Message xmlMessage) {
        LogUtil.log("MQListener : receiveRes: started  ");

        String jsonString = ConversionUtil.jmsMessageInJson(xmlMessage);
        //Create new account
        if (jsonString != null) {
            LogUtil.log("MQListener : receiveRes: Message Received newAccount JSON --->>>>: "+ jsonString);
            processCreateAccount(jsonString);
        }
        LogUtil.log("MQListener : receiveRes: completed  ");
    }


    private void processCreateAccount(String jsonString) {
        //Convert Json to NewAccount
        NewAccount newAccount = (NewAccount) ConversionUtil.jsonToObject(jsonString, NewAccount.class);
        LogUtil.log("MQListener : processCreateAccount json message converted into NewAccount object --->: " + newAccount);

        //Create new account
        if (newAccount == null) {
            LogUtil.log("MQListener : processCreateAccount New Account NOT created ---> " + newAccount);
        } else {
            userAccountService.createUserAndAccounts(newAccount);
            LogUtil.log("MQListener : processCreateAccount New Account created ---> " + newAccount);
        }
    }

}