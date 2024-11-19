package edu.sabanciuniv.week7day1.controller;

import edu.sabanciuniv.week7day1.model.FriendRequest;
import edu.sabanciuniv.week7day1.model.User;
import edu.sabanciuniv.week7day1.security.JwtUtil;
import edu.sabanciuniv.week7day1.service.FriendRequestService;
import edu.sabanciuniv.week7day1.service.GroupService;
import edu.sabanciuniv.week7day1.service.MessageService;
import edu.sabanciuniv.week7day1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;
    private final JwtUtil jwtUtil = new JwtUtil();
    @Autowired
    private MessageService messageService;
    @Autowired
    private  FriendRequestService friendRequestService ;
    @Autowired
    private  GroupService groupService ;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        User x = userService.registerUser(user);
        System.out.println(x.toString());
        return x;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User loggedInUser = userService.login(user.getEmail(), user.getPassword());
        if (loggedInUser != null) {
            // Generate JWT Token
            String token = jwtUtil.generateToken(loggedInUser.getId());
            // Return token in response
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    @PostMapping("/deleteAllDataBase")
    public void deleteAll() {
        userService.deleteAllUsers();
        messageService.deleteAllMessages();
        friendRequestService.deleteAllFriendRequests();
        groupService.deleteAllGroups();
    }

    @GetMapping("/allIds")
    public ResponseEntity<List<String>> getAllUserIds() {
        List<User> users = userService.getAllUsers();
        List<String> userIds = users.stream()
                .map(User::getId)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userIds);
    }
}