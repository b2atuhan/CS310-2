package edu.sabanciuniv.week7day1.service;

import edu.sabanciuniv.week7day1.model.Message;
import edu.sabanciuniv.week7day1.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Message sendMessage(Message message) {
        return messageRepository.save(message);
    }
    public String getMessage (String id) {
        Optional<Message> message = messageRepository.findById(id);
        return message.get().getContent();
    }
    public List<Message> getAllMessages(String UserId) {return messageRepository.findAll().stream()
            .filter(m -> UserId.equals(m.getSenderId()) || UserId.equals(m.getReceiverId()))
            .toList();    }


    public List<Message> getMessages(String groupId) {
        return messageRepository.findAll().stream()
                .filter(m -> groupId.equals(m.getReceiverId()))
                .toList();
    }
    public List<Message> getMessages(String personA,String personB) {
        return messageRepository.findAll().stream()
                .filter(m -> (personA.equals(m.getReceiverId()) && personB.equals(m.getSenderId())) ||
                        (personB.equals(m.getReceiverId()) && personA.equals(m.getSenderId())))
                .toList();
    }

    public void deleteAllMessages() {
        messageRepository.deleteAll();
    }
}