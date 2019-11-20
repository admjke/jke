package com.jke.jkemq.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository /// here is the trick
public interface NewAccountRepository extends CrudRepository<NewAccount, Long> {

    List<NewAccount> findByStatus(String status);
    List<NewAccount> findByUserName(String status);

}

