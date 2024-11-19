package edu.sabanciuniv.week7day1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("users")
public class User {
    @Id
    private String id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private List<String> friends;
    private List<String> groups;

}
