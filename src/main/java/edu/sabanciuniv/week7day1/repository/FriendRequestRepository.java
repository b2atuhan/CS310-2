package edu.sabanciuniv.week7day1.repository;

import edu.sabanciuniv.week7day1.model.FriendRequest;
import org.apache.coyote.Request;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FriendRequestRepository extends MongoRepository<FriendRequest, String> {
    FriendRequest getFriendRequestById(String id);
}