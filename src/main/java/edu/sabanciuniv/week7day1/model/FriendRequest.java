package edu.sabanciuniv.week7day1.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class FriendRequest {
    @Id
    private String id;

    @Override
    public String toString() {
        return "FriendRequest{" +
                "id='" + id + '\'' +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", status=" + status +
                '}';
    }

    @Setter
    private String senderId;
    @Setter
    private String receiverId;
    @Setter
    private Status status;
    public FriendRequest(String receiverId) {
        this.receiverId = receiverId;
    }

    // Default constructor for deserialization
    public FriendRequest() {
    }


}
