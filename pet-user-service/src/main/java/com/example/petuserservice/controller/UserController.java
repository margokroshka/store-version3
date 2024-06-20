package com.example.petuserservice.controller;

import com.example.petuserservice.model.UserRequest;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final Keycloak keycloak = KeycloakBuilder.builder()
            .serverUrl("http://your-keycloak-server/auth")
            .realm("your-realm")
            .clientId("your-client-id")
            .clientSecret("your-client-secret")
            .username("admin-username")
            .password("admin-password")
            .build();

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequest userRequest) {
        try {
            UserRepresentation user = new UserRepresentation();
            user.setUsername(userRequest.getUsername());
            user.setEmail(userRequest.getEmail());
            user.setEnabled(true);
            keycloak.realm("your-realm").users().create(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register user: " + e.getMessage());
        }
    }
}
