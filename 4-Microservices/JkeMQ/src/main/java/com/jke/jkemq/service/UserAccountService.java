package com.jke.jkemq.service;

import com.jke.jkemq.jpa.Account;
import com.jke.jkemq.jpa.AccountTypeBean;
import com.jke.jkemq.jpa.NewAccount;
import com.jke.jkemq.jpa.User;
import com.jke.jkemq.model.AccountUtilBean;
import com.jke.jkemq.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration


@Service
public class UserAccountService {

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @Autowired
    NewAccountService newAccountService;

    @Autowired
    JkeMailSender jkeMailSender;

    @Value("${jke.notification.enabled}")
    private boolean notificationEnabled;

    @Value("${jke.notification.from}")
    private String notificationFrom;

    public void createUserAndAccounts(NewAccount newAccount) {

        LogUtil.log("UserAccountService : createUserAndAccounts : Started");

        //Create User
        createUser(newAccount);

        //Create accounts
        createAccounts(newAccount);

        //update created status in newaccount table
        newAccountService.updateCreatedStatus(newAccount.getUserName());

        //Send notification to the user
        if (notificationEnabled) {
            sendNotification(newAccount);
        }

        LogUtil.log("UserAccountService : createUserAndAccounts : Completed");
    }

    private void createUser (NewAccount newAccount) {
        LogUtil.log("UserAccountService : createUser Started");

        User user = new User();
        user.setUserName(newAccount.getUserName());
        user.setPassword(newAccount.getUserName());

        user.setFirstName(newAccount.getFirstName());
        user.setLastName(newAccount.getLastName());

        user.setCompanyName(newAccount.getCompanyName());
        user.setHomeAddress(newAccount.getHomeAddress());
        user.setOfficeAddress(newAccount.getOfficeAddress());

        user.setMobileNo(newAccount.getMobileNo());
        user.setAlternateMobileNo(newAccount.getAlternateMobileNo());
        user.setEmailId(newAccount.getEmailId());

        user.setAccountType(newAccount.getAccountType());
        user.setKycDocuments(newAccount.getKycDocuments());
        user.setStatus(AccountUtilBean.ACCOUNT_STATUS_1_ACTIVE);
        user.setRemarks(newAccount.getRemarks());

        //Default role - CUSTOMER
        if (newAccount.getRoles() == null || newAccount.getRoles().isEmpty()) {
            user.setRoles(AccountUtilBean.USER_ROLE_3_CUSTOMER);
        } else {
            user.setRoles(newAccount.getRoles());
        }

        LogUtil.log("UserAccountService : createUser before save : user: " + user);

        //Create User
        userService.saveOrUpdate(user);

        LogUtil.log("UserAccountService : createUser Completed");
    }


     private void createAccounts(NewAccount newAccount) {
        LogUtil.log("UserAccountService : createAccounts Started");

        //Create accounts with 4 different account types
        double balance = 20000;
        double dividends = 10000;

        Account account = new Account();
        account.setUserName(newAccount.getUserName());
        account.setBalance(balance);
        account.setAccounttype(AccountTypeBean.IRA.name());
        account.setDividends(dividends);
        account.setDividendsetd(dividends);
        accountService.saveOrUpdate(account);

        account = new Account();
        account.setUserName(newAccount.getUserName());
        account.setBalance(balance);
        account.setAccounttype(AccountTypeBean.Checking.name());
        account.setDividends(dividends);
        account.setDividendsetd(dividends);
        accountService.saveOrUpdate(account);

        account = new Account();
        account.setUserName(newAccount.getUserName());
        account.setBalance(balance);
        account.setAccounttype(AccountTypeBean.Savings.name());
        account.setDividends(dividends);
        account.setDividendsetd(dividends);
        accountService.saveOrUpdate(account);

        account = new Account();
        account.setUserName(newAccount.getUserName());
        account.setBalance(balance);
        account.setAccounttype(AccountTypeBean.Money_Market.name());
        account.setDividends(dividends);
        account.setDividendsetd(dividends);
        accountService.saveOrUpdate(account);

        LogUtil.log("UserAccountService : createAccounts Completed");
    }

    private void sendNotification (NewAccount newAccount) {
        LogUtil.log("sendNotification Started");

        String from = notificationFrom;
        String to = newAccount.getEmailId();
        String cc = notificationFrom;
        String subject = "JKE Bank - New account created for the user : " + newAccount.getUserName();

        String msg = "Dear " + newAccount.getFirstName() + " " + newAccount.getLastName() + ", \n\n";
        msg += "Your JKE Bank account has been created. You can access your account using the credentials  " + newAccount.getUserName() + "/" + newAccount.getUserName() + " \n\n";
        msg += "Thanks and Regards, \n\n";
        msg += "JKE Bank Account Processing Team \n";

        jkeMailSender.sendMail(from, to, cc, subject, msg);

        LogUtil.log("sendNotification Completed");
    }

}
