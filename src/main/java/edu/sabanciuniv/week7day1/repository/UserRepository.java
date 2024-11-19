package edu.sabanciuniv.week7day1.repository;

import edu.sabanciuniv.week7day1.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}