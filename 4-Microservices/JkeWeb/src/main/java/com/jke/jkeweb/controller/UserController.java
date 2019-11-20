package com.jke.jkeweb.controller;

import com.jke.jkeweb.jpa.NewAccount;
import com.jke.jkeweb.jpa.User;
import com.jke.jkeweb.model.ApiResponse;
import com.jke.jkeweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    private List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/usersWithRoleCustomer")
    private List<User> getAllUsersWithRoleCustomers() {
        return userService.getAllUsersWithRoleCustomers();
    }

    @GetMapping("/serverInfo")
    private ApiResponse getServerInfo() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setUrl(userService.getServerInfo());
        return apiResponse;
    }


}

