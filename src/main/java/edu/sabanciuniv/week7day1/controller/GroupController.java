package edu.sabanciuniv.week7day1.controller;

import edu.sabanciuniv.week7day1.model.Message;
import edu.sabanciuniv.week7day1.repository.GroupRepository;
import edu.sabanciuniv.week7day1.repository.MessageRepository;
import edu.sabanciuniv.week7day1.security.JwtUtil;
import edu.sabanciuniv.week7day1.model.Group;
import edu.sabanciuniv.week7day1.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final JwtUtil jwtUtil = new JwtUtil();

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupRepository groupRepository;

    @PostMapping("/create")
    public Group createGroup(@RequestBody Group group, @RequestHeader("Authorization") String authorizationHeader) {
        String currentUserId = extractSenderIdFromJwt(authorizationHeader);
        List<String> members = group.getMembers();
        members.add(currentUserId);
        group.setMembers(members);
        return groupService.createGroup(group);
    }

    @PostMapping("/{groupId}/add-member")
    public Group addMemberToGroup(@PathVariable String groupId, @RequestBody Map<String, String> memberId) {
        String id = memberId.get("id");
        return groupService.addMemberToGroup(groupId, id);
    }

    @GetMapping("/{groupId}/members")
    public List<String> members(@PathVariable String groupId, @RequestHeader("Authorization") String authorizationHeader) {
        String currentUserId = extractSenderIdFromJwt(authorizationHeader);
        Group group = groupRepository.getGroupById(groupId);
        if(!group.getMembers().contains(currentUserId)) {
            return null;
        }
        return group.getMembers();
    }

    @PostMapping("/{groupId}/send")
    public Group sendMessageToGroup(
            @PathVariable String groupId,
            @RequestBody Message message,
            @RequestHeader("Authorization") String authorizationHeader) {
        // Extract sender ID from JWT
        String senderId = extractSenderIdFromJwt(authorizationHeader);

        // Retrieve the group
        Group group = groupRepository.getGroupById(groupId);
        if (group == null || !group.getMembers().contains(senderId)) {
            throw new IllegalArgumentException("You are not authorized to send messages to this group.");
        }
        System.out.println(message.toString());
        // Send message to all group members except the sender

        Message message1= groupService.sendMessageToGroup(groupId, message, senderId);// Optionally, return the updated group object
        List<String> temp=group.getMessages();
        temp.add(message1.getId());
        group.setMessages(temp);
        groupRepository.save(group);
        return group;
    }

    @GetMapping("/{groupId}/messages")
    public List<Message> getGroupMessages(
            @PathVariable String groupId,
            @RequestHeader("Authorization") String authorizationHeader) {
        // Extract sender ID from JWT
        String currentUserId = extractSenderIdFromJwt(authorizationHeader);

        // Retrieve the group
        Group group = groupRepository.getGroupById(groupId);
        if (group == null || !group.getMembers().contains(currentUserId)) {
            throw new IllegalArgumentException("You are not authorized to view messages for this group.");
        }

        // Fetch all messages by their IDs (assuming a MessageRepository exists)
        List<String> messageIds = group.getMessages();
        return messageRepository.findAllById(messageIds);
    }

    private String extractSenderIdFromJwt(String authorizationHeader) {
        // Remove "Bearer " prefix and decode JWT to get senderId
        String token = authorizationHeader.replace("Bearer ", "");
        return jwtUtil.extractid(token); // Replace JwtUtils with your implementation
    }
}