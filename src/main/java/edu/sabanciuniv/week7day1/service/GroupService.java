package edu.sabanciuniv.week7day1.service;

import edu.sabanciuniv.week7day1.model.Group;
import edu.sabanciuniv.week7day1.model.Message;
import edu.sabanciuniv.week7day1.repository.GroupRepository;
import edu.sabanciuniv.week7day1.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;


    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageRepository messageRepository;

    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    public Group addMemberToGroup(String groupId, String memberId) {
        Optional<Group> group = groupRepository.findById(groupId);
        if (group.isPresent()) {
            Group g = group.get();
            g.getMembers().add(memberId);
            return groupRepository.save(g);
        }
        return null;
    }

    public Message sendMessageToGroup(String groupId, Message message, String senderId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        Message temp = new Message();
        if (groupOptional.isPresent()) {
            Group group = groupOptional.get();

            for (String memberId : group.getMembers()) {
                if (!memberId.equals(senderId)) {
                    Message newMessage = new Message();
                    newMessage.setSenderId(senderId);
                    newMessage.setReceiverId(memberId);
                    newMessage.setContent(message.getContent());

                    // Use the injected MessageService
                    temp= messageService.sendMessage(newMessage);
                }
            }
        } else {
            throw new IllegalArgumentException("Group not found.");
        }
        return temp;
    }


    public void deleteAllGroups() {
        groupRepository.deleteAll();
    }
}