package com.nbenja.jwtexample.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String username;
    private String password;
    private List<String> roles;
    private String firstName;
    private String lastName;

    public User(String username) {
        this.username = username;
    }
}
