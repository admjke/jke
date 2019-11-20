package com.jke.jkemq.controller;

import com.jke.jkemq.jpa.NewAccount;
import com.jke.jkemq.service.MQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/mq/")
public class MQController {

    @Autowired
    MQSender mQSender;

    @Autowired
    private JmsTemplate jmsTemplate;

    @GetMapping(value = "/send")
    public String send(@RequestParam String requestId,  @RequestParam String userName, @RequestParam String firstName, @RequestParam String lastName) {

        NewAccount newAccount = new NewAccount();
//        newAccount.setRequestId(requestId);
        newAccount.setUserName(userName);
        newAccount.setFirstName(firstName);
        newAccount.setLastName(lastName);

        mQSender.sendReq(newAccount);

        return "Message sent to the MQ  Successfully";
    }

}
