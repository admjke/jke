package com.jke.jkemq.controller;

import com.jke.jkemq.jpa.Account;
import com.jke.jkemq.service.AccountService;
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
    private List<Account> getAllAccountsByUserName(@RequestParam String userName) {
        return accountService.getAllAccounts(userName);
    }

    @GetMapping("/accounts/{id}")
    private Account getAccount(@PathVariable Long id) {
        return accountService.getAccount(id);
    }

}
