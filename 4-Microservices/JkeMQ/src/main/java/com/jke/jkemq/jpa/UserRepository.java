package com.jke.jkemq.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository /// here is the trick
public interface UserRepository extends CrudRepository<User, Long> {

}

