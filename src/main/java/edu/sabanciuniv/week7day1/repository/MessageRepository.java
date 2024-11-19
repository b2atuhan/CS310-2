package edu.sabanciuniv.week7day1.repository;

import edu.sabanciuniv.week7day1.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {}
