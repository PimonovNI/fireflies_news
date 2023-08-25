package com.project.fireflies.repository;

import com.project.fireflies.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<User, ObjectId> {

    Optional<User> findByUsername(String username);

    @Query("{'activation_code': '?0'}")
    Optional<User> findByActivationCode(String activationCode);

}
