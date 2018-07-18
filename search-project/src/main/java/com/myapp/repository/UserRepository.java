package com.myapp.repository;

import com.myapp.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author cgh
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByName(String name);
}
