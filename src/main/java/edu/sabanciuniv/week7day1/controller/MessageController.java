package edu.sabanciuniv.week7day1.controller;
import edu.sabanciuniv.week7day1.model.User;
import edu.sabanciuniv.week7day1.security.JwtUtil;

import edu.sabanciuniv.week7day1.model.FriendRequest;
import edu.sabanciuniv.week7day1.model.Message;
import edu.sabanciuniv.week7day1.model.Status;
import edu.sabanciuniv.week7day1.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MessageController {


    @Autowired

    private MessageService messageService;
    private final JwtUtil jwtUtil = new JwtUtil();


    @PostMapping(value = "/messages/send")
    public Message sendMessage(@RequestBody Message message, @RequestHeader("Authorization") String authorizationHeader) {
        String senderId = extractSenderIdFromJwt(authorizationHeader);


        message.setSenderId(senderId);


        return messageService.sendMessage(message);
    }
    @GetMapping(value = "/messages/get")
    public String getMessage(String id) {

        return messageService.getMessage(id);
    }


    @GetMapping("/messages")
    public List<Message> getAllMessages(@RequestHeader("Authorization") String authorizationHeader) {
        String userId = extractSenderIdFromJwt(authorizationHeader);
        return messageService.getAllMessages(userId);
    }
    @GetMapping("/messages/{id}")
    public List<Message> getAllMessages(@PathVariable String id, @RequestHeader("Authorization") String authorizationHeader) {
        String currentUserId = extractSenderIdFromJwt(authorizationHeader);

        return messageService.getMessages(currentUserId,id);

    }



    private String extractSenderIdFromJwt(String authorizationHeader) {
        // Remove "Bearer " prefix and decode JWT to get senderId
        String token = authorizationHeader.replace("Bearer ", "");
        return jwtUtil.extractid(token); // Replace JwtUtils with your implementation
    }


}
