

package com.dev.ritividhi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String userId;
    private String name;
    private String email;
    private String phone;
    private List<String> role;
    private String eventName;
    private Date eventDate; 
    private String gender;  
    @DBRef
    private Address address;
}
