package com.jke.jkemq.service;

import com.jke.jkemq.jpa.Account;
import com.jke.jkemq.jpa.AccountRepository;
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

    public Account getAccount(Long id) {
        return accountRepository.findById(id).get();
    }

    public void saveOrUpdate(Account account) {
        accountRepository.save(account);
    }

    public void delete(String userName) {
        //delete accounts
        accountRepository.deleteByUserName(userName);
    }
    
}
