package com.example.petuserservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @Id
    private Integer id;
    private String username;
    private String email;

    public UserRequest(String username, String email) {

    }
}
