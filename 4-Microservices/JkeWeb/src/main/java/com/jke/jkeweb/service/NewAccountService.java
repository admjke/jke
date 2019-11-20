package com.jke.jkeweb.service;

import com.jke.beans.AccountUtilBean;
import com.jke.jkeweb.jpa.NewAccount;
import com.jke.jkeweb.jpa.NewAccountRepository;
import com.jke.jkeweb.jpa.User;
import com.jke.jkeweb.model.ApiResponse;
import com.jke.jkeweb.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewAccountService {

    @Autowired
    NewAccountRepository newAccountRepository;

    @Autowired
    UserService userService;

    @Autowired
    MQSender mQSender;

    public List<NewAccount> getAll() {
        List<NewAccount> list = new ArrayList();
        newAccountRepository.findAll().forEach(newAccount -> list.add(newAccount));
        return list;
    }

    public NewAccount getById(Long accountId) {
        return newAccountRepository.findById(accountId).get();
    }

    public List<NewAccount> getAll(String status) {
        List<NewAccount> list = new ArrayList();
        newAccountRepository.findByStatus(status).forEach(newAccount -> list.add(newAccount));
        return list;
    }

    public Long save(NewAccount newAccount) {
        newAccount = newAccountRepository.save(newAccount);
        Long id = null;
        if (newAccount != null) {
            id = newAccount.getId();
        }
        return id;
    }

    public void approveAccount(Long id) {
        LogUtil.log("approveAccount : started -->" + id);

        NewAccount newAccount = newAccountRepository.findById(id).get();

        if (newAccount == null) {
            LogUtil.log("approveAccount : Account id : " + id + " doesn't exists");
        } else {

            //Put in Queue
            LogUtil.log("approveAccount : Put in Queue ");
            mQSender.send(newAccount);

            //Update the status of new Account
            LogUtil.log("approveAccount : Update New Account Status ");
            newAccount.setStatus(AccountUtilBean.NEW_ACCOUNT_STATUS_2_APPROVED);
            newAccountRepository.save(newAccount);
        }
        LogUtil.log("approveAccount : completed -->" + id);
    }

    public void rejectAccount(Long id) {

        NewAccount newAccount = newAccountRepository.findById(id).get();
        if (newAccount != null) {
            newAccount.setStatus(AccountUtilBean.NEW_ACCOUNT_STATUS_3_REJECTED);
            newAccountRepository.save(newAccount);
        }
    }

    public void deleteAccount(Long id) {

        NewAccount newAccount = newAccountRepository.findById(id).get();
        if (newAccount != null) {
            newAccount.setStatus(AccountUtilBean.NEW_ACCOUNT_STATUS_3_REJECTED);
            newAccountRepository.delete(newAccount);
        }
    }
    public ApiResponse isValidNewUser(String userName) {
        boolean validUser = true;
        String errorMsg = null;

        if (userService.isUserExists(userName)) {
            validUser = false;
            errorMsg = "User name already exists.";
        } else if (isDuplicateUserExists(userName)) {
            validUser = false;
            errorMsg = "User name already requested.";
        } else {
            validUser = true;
            errorMsg = "";
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setBoolValue1(validUser);
        apiResponse.setValue1(errorMsg);

        return apiResponse;
    }

    public boolean isDuplicateUserExists(String userName) {
        boolean duplicate = false;
        List<NewAccount> newAccounts = newAccountRepository.findByUserName(userName);
        if (newAccounts == null || newAccounts.size() == 0) {
            duplicate = false;
        } else if (newAccounts.size() == 1) {
            //If the entry found with the status other than REQUESTED, then it is considered as duplicate
            if (!AccountUtilBean.NEW_ACCOUNT_STATUS_1_REQUESTED.equalsIgnoreCase(newAccounts.get(0).getStatus())) {
                duplicate = true;
            }
        } else if (newAccounts.size() > 1) {
            int count = 0;
            //If the entry found with the status other than REQUESTED, then it is considered as duplicate
            for (NewAccount newAccount : newAccounts) {
                if (!AccountUtilBean.NEW_ACCOUNT_STATUS_1_REQUESTED.equalsIgnoreCase(newAccount.getStatus())) {
                    count ++;
                }
                if (count > 1) {
                    duplicate = true;
                    break;
                }
            }
        }
        return duplicate;
    }
}
