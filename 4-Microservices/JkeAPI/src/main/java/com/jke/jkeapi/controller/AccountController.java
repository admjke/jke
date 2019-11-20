package com.jke.jkeapi.controller;

import com.jke.jkeapi.jpa.Account;
import com.jke.jkeapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController

public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/accounts")
    private List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/accountsByUserName")
    private List<Account> getAllAccountsByUserName(@RequestParam("userName") String userName) {
        return accountService.getAllAccounts(userName);
    }

    @GetMapping("/accounts/{id}")
    private Account getAccount(@PathVariable("id") int id) {
        return accountService.getAccount(id);
    }

}
