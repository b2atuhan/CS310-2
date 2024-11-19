package edu.sabanciuniv.week7day1.controller;
import edu.sabanciuniv.week7day1.model.Status;
import edu.sabanciuniv.week7day1.repository.FriendRequestRepository;
import edu.sabanciuniv.week7day1.security.JwtUtil;

import edu.sabanciuniv.week7day1.model.FriendRequest;
import edu.sabanciuniv.week7day1.model.User;
import edu.sabanciuniv.week7day1.service.FriendRequestService;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/friend")
public class FriendController {
    @Autowired

    private FriendRequestService friendRequestService;
    private final JwtUtil jwtUtil = new JwtUtil();
    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @PostMapping(value = "/add")
    public FriendRequest sendFriendRequest(@RequestBody FriendRequest request, @RequestHeader("Authorization") String authorizationHeader) {        // Save the FriendRequest
        // Extract senderId from JWT
        String senderId = extractSenderIdFromJwt(authorizationHeader);
        request.setSenderId(senderId);
        request.setStatus(Status.PENDING);
        return friendRequestService.sendFriendRequest(request);
    }



    private String extractSenderIdFromJwt(String authorizationHeader) {
        // Remove "Bearer " prefix and decode JWT to get senderId
        String token = authorizationHeader.replace("Bearer ", "");
        return jwtUtil.extractid(token); // Replace JwtUtils with your implementation
    }

    @PostMapping("/accept")
    public boolean acceptFriendRequest(@RequestBody Map <String , String> requestId, @RequestHeader("Authorization") String authorizationHeader) {
        String currentUserId = extractSenderIdFromJwt(authorizationHeader);
        FriendRequest request= friendRequestRepository.getFriendRequestById(requestId.get("id"));
        if(request.getReceiverId().equals(currentUserId)) {
            return friendRequestService.acceptFriendRequest(requestId , true);
        }
        else {
            return false;
        }
    }

    @GetMapping("/friends/{userId}")
    public List<String> getFriends(@PathVariable String userId) {
        return friendRequestService.getFriends(userId);
    }
}