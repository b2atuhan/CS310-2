package edu.sabanciuniv.week7day1.service;

import edu.sabanciuniv.week7day1.model.*;
import edu.sabanciuniv.week7day1.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FriendRequestService {
    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private UserRepository userRepository;

    public FriendRequest sendFriendRequest(FriendRequest request) {
        return friendRequestRepository.save(request);
    }

    public boolean acceptFriendRequest(Map<String, String> request , boolean isAccepted) {
        // Find the FriendRequest by ID
        String id = request.get("id");
        Optional<FriendRequest> Frequest = friendRequestRepository.findById(id);
        if (Frequest.isPresent()) {
            FriendRequest friendRequest = Frequest.get();

            // Update the status to ACCEPTED
            if (isAccepted) {
                Optional<User> sender = userRepository.findById(friendRequest.getSenderId());
                sender.get().getFriends().add(friendRequest.getReceiverId());
                userRepository.save(sender.get());
                Optional<User> reciver = userRepository.findById(friendRequest.getReceiverId());
                reciver.get().getFriends().add(friendRequest.getSenderId());
                userRepository.save(reciver.get());

                friendRequest.setStatus(Status.ACCEPTED);
            } else
                friendRequest.setStatus(Status.REJECTED);
            // Save the updated FriendRequest back to the repository
            friendRequestRepository.save(friendRequest);
            return true; // Indicate the request was successfully accepted
        }

        // If the request is not found, return false
        return false;
    }

    public List<String> getFriends(String userId) {

        Optional<User> user = userRepository.findById(userId);

        return user.get().getFriends();

    }
    public void deleteAllFriendRequests() {
        friendRequestRepository.deleteAll();
    }
}