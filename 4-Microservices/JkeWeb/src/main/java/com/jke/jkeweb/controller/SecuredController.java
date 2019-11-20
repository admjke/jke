package com.jke.jkeweb.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

public class SecuredController {
    @RequestMapping("/secured")
    public String secured(){
        System.out.println("Inside secured()ß");
        return "Hello user !!! : " + new Date();
    }
}
