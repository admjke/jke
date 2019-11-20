package com.jke.jkeapi.service;

import com.jke.jkeapi.jpa.User;
import com.jke.jkeapi.jpa.UserRepository;
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

    public User getUser(int id) {
        return userRepository.findById(id).get();
    }

}
