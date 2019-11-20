package com.jke.jkeapi.service;

import com.jke.jkeapi.jpa.Account;
import com.jke.jkeapi.jpa.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList();
        accountRepository.findAll().forEach(account -> accounts.add(account));
        return accounts;
    }

    public List<Account> getAllAccounts(String userName) {
        List<Account> accounts = new ArrayList();
        accountRepository.findByUserName(userName).forEach(account -> accounts.add(account));
        return accounts;
    }

    public Account getAccount(int id) {
        return accountRepository.findById(id).get();
    }


}
