package com.jke.jkeweb.controller;

import com.jke.beans.AccountUtilBean;
import com.jke.jkeweb.jpa.NewAccount;
import com.jke.jkeweb.model.ApiResponse;
import com.jke.jkeweb.service.NewAccountService;
import com.jke.jkeweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class NewAccountController {

    @Autowired
    NewAccountService newAccountService;

    @GetMapping("/newAccounts")
    private List<NewAccount> getAll() {
        return newAccountService.getAll();
    }

    @GetMapping("/newAccount/{accountId}")
    private NewAccount getById(@PathVariable("accountId") Long accountId) {
        return newAccountService.getById(accountId);
    }

    @GetMapping("/newAccountsByStatus")
    private List<NewAccount> getAllByStatus(@RequestParam("status") String status) {
        return newAccountService.getAll(status);
    }

    @PostMapping(path= "/saveNewAccount", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = "text/plain")
    private ResponseEntity<String> saveNewAccount(@QueryParam("accountId") Long accountId, @QueryParam("userName") String userName, @QueryParam("firstName") String firstName, @QueryParam("lastName")  String lastName,
                                                         @QueryParam("companyName") String companyName, @QueryParam("homeAddress") String homeAddress, @QueryParam("officeAddress")  String officeAddress,
                                                         @QueryParam("mobileNo") String mobileNo, @QueryParam("alternateMobileNo") String alternateMobileNo, @QueryParam("emailId")  String emailId,
                                                         @QueryParam("accountType") String accountType, @QueryParam("kycDocuments") String kycDocuments, @QueryParam("remarks")  String remarks) {

        NewAccount newAccount = new NewAccount();
//        newAccount.setRequestId(System.currentTimeMillis() + "");

        //If not null and zero - Modify
        if (accountId != null && accountId != 0) {
            newAccount.setId(accountId);
        }

        newAccount.setUserName(userName);
        newAccount.setFirstName(firstName);
        newAccount.setLastName(lastName);
        newAccount.setCompanyName(companyName);
        newAccount.setHomeAddress(homeAddress);
        newAccount.setOfficeAddress(officeAddress);
        newAccount.setMobileNo(mobileNo);
        newAccount.setAlternateMobileNo(alternateMobileNo);
        newAccount.setEmailId(emailId);
        newAccount.setAccountType(accountType);
        newAccount.setKycDocuments(kycDocuments);
        newAccount.setRemarks(remarks);
        newAccount.setRoles(AccountUtilBean.USER_ROLE_3_CUSTOMER);
        newAccount.setStatus(AccountUtilBean.NEW_ACCOUNT_STATUS_1_REQUESTED);

        System.out.println(" User account create 1--->" + newAccount);

        newAccountService.save(newAccount);

        System.out.println(" User account create 2--->" + newAccount);

        return new ResponseEntity<>(newAccount.getUserName(), new HttpHeaders(), HttpStatus.CREATED);
    }

    @PostMapping(path= "/approveAccount", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = "text/plain")
    private ResponseEntity<String> approveAccount(@QueryParam("id") Long id) {
        System.out.println("id--->" + id);

        newAccountService.approveAccount(id);

        return new ResponseEntity<>("Success", new HttpHeaders(), HttpStatus.CREATED);
    }

    @PostMapping(path= "/rejectAccount", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = "text/plain")
    private ResponseEntity<String> rejectAccount(@QueryParam("id") Long id) {

        newAccountService.rejectAccount(id);

        return new ResponseEntity<>("Success", new HttpHeaders(), HttpStatus.CREATED);
    }

    @PostMapping(path= "/deleteAccount", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = "text/plain")
    private ResponseEntity<String> deleteAccount(@QueryParam("id") Long id) {

        newAccountService.deleteAccount(id);

        return new ResponseEntity<>("Success", new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/validUser/{userName}")
    private ApiResponse isValidNewUser(@PathVariable("userName") String userName) {
        return newAccountService.isValidNewUser(userName);
    }



}

