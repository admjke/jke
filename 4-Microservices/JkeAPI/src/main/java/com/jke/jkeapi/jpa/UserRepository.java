package com.jke.jkeapi.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository /// here is the trick
public interface UserRepository extends CrudRepository<User, Integer> {
}

