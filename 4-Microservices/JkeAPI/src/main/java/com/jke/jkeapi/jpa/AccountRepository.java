package com.jke.jkeapi.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository /// here is the trick
public interface AccountRepository extends CrudRepository<Account, Integer> {
    List<Account> findByUserName(String userName);
}

