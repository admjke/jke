package com.jke.jkeweb.service;

import com.jke.beans.AccountUtilBean;
import com.jke.jkeweb.jpa.User;
import com.jke.jkeweb.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        List<User> users = new ArrayList();
        userRepository.findAll().forEach(user -> users.add(user));

        //Empty the password
        for (User user : users) {
            user.setPassword("");
        }

        return users;
    }


    public List<User> getAllUsersWithRoleCustomers() {
        List<User> users = new ArrayList();
        userRepository.findByRoles(AccountUtilBean.USER_ROLE_3_CUSTOMER).forEach(user -> users.add(user));

        //Empty the password
        for (User user : users) {
            user.setPassword("");
        }

        return users;
    }

    public boolean isUserExists(String userName) {
        boolean result = false;
        User user = userRepository.findByUserName(userName);
        if (user != null) {
            result = true;
        }
        return result;
    }

    public String getServerInfo() {
        String result = System.getenv("HOSTNAME");
        System.out.println("ServerInfo --->" + result);
        return result;
    }
}
