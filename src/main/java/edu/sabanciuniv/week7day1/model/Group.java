package edu.sabanciuniv.week7day1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("groups")
public class Group {
    @Id
    private String id;
    private String name;
    private List<String> members = new ArrayList<>();;
    private List<String> messages =new ArrayList<>();;
}