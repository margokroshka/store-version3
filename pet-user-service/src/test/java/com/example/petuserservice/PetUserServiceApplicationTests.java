package com.example.petuserservice;

import com.example.petuserservice.controller.UserController;
import com.example.petuserservice.model.UserRequest;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


class UserControllerTest {

    @Mock
    private Keycloak keycloak;

    @InjectMocks
    private UserController userController;

    public UserControllerTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        UserRequest userRequest = new UserRequest("Margo", "email");

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(userRequest.getUsername());
        userRepresentation.setEmail(userRequest.getEmail());
        userRepresentation.setEnabled(true);

        ResponseEntity<String> response = userController.registerUser(userRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody());

        verify(keycloak.realm("your-realm").users(), times(1)).create(userRepresentation);
    }

    @Test
    void testRegisterUser_Failure() {
        UserRequest userRequest = new UserRequest("Margarita", "email123");
        doThrow(new RuntimeException("Failed to create user")).when(keycloak.realm("your-realm").users()).create(any(UserRepresentation.class));

        ResponseEntity<String> response = userController.registerUser(userRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to register user: Failed to create user", response.getBody());
    }
}
