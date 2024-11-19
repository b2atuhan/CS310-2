package edu.sabanciuniv.week7day1.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("messages")
@Getter
@Setter
public class Message {
    @Id
    private String id;


    @Setter
    private String senderId;
    @Setter
    private String receiverId;
    @Setter
    private String content;


    public Message(String receiverId) {
        this.receiverId = receiverId;
    }

    // Default constructor for deserialization
    public Message() {
    }
    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", content=" + content +
                '}';
    }
}