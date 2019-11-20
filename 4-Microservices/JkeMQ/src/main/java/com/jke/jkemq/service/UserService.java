package com.jke.jkemq.service;

import com.jke.jkemq.jpa.User;
import com.jke.jkemq.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
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

    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    public void saveOrUpdate(User user) {
        userRepository.save(user);
    }

}
