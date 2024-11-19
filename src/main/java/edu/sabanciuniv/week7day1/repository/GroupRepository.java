package edu.sabanciuniv.week7day1.repository;

import edu.sabanciuniv.week7day1.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group, String> {
    Group getGroupById(String groupId);
}

