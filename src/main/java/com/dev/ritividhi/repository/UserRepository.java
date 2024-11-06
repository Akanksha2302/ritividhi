package com.dev.ritividhi.repository;



import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dev.ritividhi.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String emailId);

	Optional<User> findByPhone(String identifier);

	Optional<User> findByEmailOrPhone(String emailOrPhone, String emailOrPhone2);

}
