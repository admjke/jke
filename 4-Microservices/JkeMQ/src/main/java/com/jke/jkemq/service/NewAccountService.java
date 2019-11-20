package com.jke.jkemq.service;


import com.jke.jkemq.jpa.NewAccount;
import com.jke.jkemq.jpa.NewAccountRepository;
import com.jke.jkemq.model.AccountUtilBean;
import com.jke.jkemq.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewAccountService {

    @Autowired
    NewAccountRepository newAccountRepository;

    public void updateCreatedStatus(String userName) {

        LogUtil.log(("NewAccountService : updateCreatedStatus : started -->" + userName));

        List<NewAccount> newAccounts = newAccountRepository.findByUserName(userName);
        LogUtil.log(("NewAccountService : updateCreatedStatus : newAccounts -->" + newAccounts));

        if (newAccounts == null || newAccounts.size() == 0) {
            LogUtil.log("NewAccountService : updateCreatedStatus : Account userName : " + userName + " doesn't exists");
        } else {
            //Update the status of new Account
            NewAccount newAccount = newAccounts.get(0);
            newAccount.setStatus(AccountUtilBean.NEW_ACCOUNT_STATUS_4_CREATED);
            newAccountRepository.save(newAccount);
            LogUtil.log("NewAccountService : updateCreatedStatus : status updated-->" + newAccount);
        }

        LogUtil.log("NewAccountService : updateCreatedStatus : completed-->");
    }
}
