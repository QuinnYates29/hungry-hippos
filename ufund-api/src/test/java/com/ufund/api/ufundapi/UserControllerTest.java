package com.ufund.api.ufundapi;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufund.api.ufundapi.controller.UserController;
import com.ufund.api.ufundapi.controller.UserController.LoginRequest;
import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.persistence.UserDAO;

/**
 * Test file for User Controller class - API/Business Layer
 * @author qry3977
 */

class UserControllerTest {
    private UserController controller;
    private UserDAO mockDAO;
    
    @BeforeEach
    void setUp() {
        mockDAO = Mockito.mock(UserDAO.class);
        controller = new UserController(mockDAO);
    }

    @Test
    void testGetAllUsers_ok() throws IOException {
        User[] users = new User[3];
        users[0] = new User(1, "bob", "123", "ADMIN");
        users[1] = new User(1, "joe", "321", "HELPER");
        users[2] = new User(1, "bob", "123", "HELPER");

        when(mockDAO.getUsers()).thenReturn(users);
        ResponseEntity<User[]> response = controller.getAllUsers();
        User[] result = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals(1, result[0].getId());
        assertEquals("bob", result[0].getUsername());
        assertEquals("123", result[0].getPassword());
        assertEquals("ADMIN", result[0].getRole());
    }

    @Test
    void testGetAllUsers_notFound() throws IOException {
        when(mockDAO.getUsers()).thenReturn(null);
        ResponseEntity<User[]> response = controller.getAllUsers();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetAllUsers_internalServerErr() throws IOException {
        when(mockDAO.getUsers()).thenThrow(new IOException());
        ResponseEntity<User[]> response = controller.getAllUsers();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    //================
    // Tests for getUser()
    //================

    @Test
    void testGetUser_ok() throws IOException {
        User user = new User(1, "bob", "123", "ADMIN");

        when(mockDAO.getUser(1)).thenReturn(user);
        ResponseEntity<User> response = controller.getUser(1);
        User result = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("bob", result.getUsername());
        assertEquals("123", result.getPassword());
        assertEquals("ADMIN", result.getRole());
    }

    @Test
    void testGetUser_notFound() throws IOException {
        when(mockDAO.getUser(1)).thenReturn(null);
        ResponseEntity<User> response = controller.getUser(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetUser_internalServerErr() throws IOException {
        when(mockDAO.getUser(1)).thenThrow(new IOException());
        ResponseEntity<User> response = controller.getUser(1);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    //================
    // Tests for login()
    //================
    
    @Test
    void testLogin_correct() throws IOException {
        User existingUser = new User(1, "bob", "123", "ADMIN");
        LoginRequest request = new LoginRequest("bob", "123");
        when(mockDAO.findByUsername("bob")).thenReturn(existingUser);

        ResponseEntity<User> response = controller.login(request);
        User result = response.getBody();

        assertNotNull(result);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(existingUser, result);
        assertEquals(1, result.getId());
        assertEquals("bob", result.getUsername());
        assertEquals("123", result.getPassword());
        assertEquals("ADMIN", result.getRole());
    }

    @Test
    void testLogin_incorrectPassword() throws IOException {
        User existingUser = new User(1, "bob", "123", "ADMIN");
        LoginRequest request = new LoginRequest("bob", "wrong_password");
        when(mockDAO.findByUsername("bob")).thenReturn(existingUser);

        ResponseEntity<User> response = controller.login(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

        
    @Test
    void testLogin_incorrectUsername() throws IOException {
        User helper = new User(1, "wrong_username", "123", "HELPER");
        LoginRequest request = new LoginRequest("wrong_username", "123");

        when(mockDAO.findByUsername("wrong_username")).thenReturn(null);
        when(mockDAO.createHelper("wrong_username", "123")).thenReturn(helper);

        ResponseEntity<User> response = controller.login(request);
        User result = response.getBody();

        assertNotNull(result);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        
        assertEquals(helper, result);
        assertEquals(1, result.getId());
        assertEquals("wrong_username", result.getUsername());
        assertEquals("123", result.getPassword());
        assertEquals("HELPER", result.getRole());
    }

    @Test
    void testLogin_internalServerError() throws IOException {
        LoginRequest request = new LoginRequest("bob", "123");
        when(mockDAO.findByUsername("bob")).thenThrow(new IOException());
        ResponseEntity<User> response = controller.login(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    //================
    // Tests for createUser()
    //================

    @Test
    void testCreateUser() throws IOException {
        User newUser = new User(1, "joe", "123", "HELPER");
        when(mockDAO.createHelper("joe", "123")).thenReturn(newUser);
        ResponseEntity<User> response = controller.createUser("joe", "123");
        User result = response.getBody();

        assertNotNull(result);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertEquals(newUser, result);
        assertEquals(1, result.getId());
        assertEquals("joe", result.getUsername());
        assertEquals("123", result.getPassword());
        assertEquals("HELPER", result.getRole());
    }

    @Test
    void testCreateUser_internalError() throws IOException {
        when(mockDAO.createHelper("user", "pass")).thenThrow(new IOException());
        ResponseEntity<User> response = controller.createUser("user", "pass");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}