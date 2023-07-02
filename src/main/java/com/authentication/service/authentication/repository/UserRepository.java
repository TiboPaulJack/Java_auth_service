package com.authentication.service.authentication.repository;

import com.authentication.service.authentication.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);

}
