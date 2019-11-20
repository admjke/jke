package com.jke.jkeweb.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository /// here is the trick
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByRoles(String roles);
    User findByUserName(String userName);

}

