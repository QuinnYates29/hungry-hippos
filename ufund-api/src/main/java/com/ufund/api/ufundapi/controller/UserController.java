/**
 * Controller class for managing user accounts and login
 * Provides endpoints to fetch and create users, and authenticate login
 * @author qry3977
 */
package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.persistence.UserDAO;


@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger LOG = Logger.getLogger(UserController.class.getName());

    private final UserDAO userDao;

    public UserController(UserDAO userDao) {
        this.userDao = userDao;
    }

    /**
     * Retrieves all users.
     * 
     * @return array of all users
     */
    @GetMapping
    public ResponseEntity<User[]> getAllUsers() {
        try {
            return ResponseEntity.ok(userDao.getUsers());
        } catch (IOException e) {
            LOG.severe("Failed to read users: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieves a single user by their ID.
     * 
     * @param id the user ID
     * @return the user object or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        try {
            User user = userDao.getUser(id);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(user);
        } catch (IOException e) {
            LOG.severe("Failed to read user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Authenticates a user with username and password.
     * 
     * @param loginRequest user credentials
     * @return the user object if login succeeds, 401 if invalid
     */
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userDao.findByUsername(loginRequest.username);
            if (user != null && user.getPassword().equals(loginRequest.password)) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (IOException e) {
            LOG.severe("Login failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Creates a new user.
     * 
     * @param user user to create
     * @return the created user
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User created = userDao.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IOException e) {
            LOG.severe("Failed to create user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Simple Data Transfer Object for login request.
     */
    public static class LoginRequest {
        public String username;
        public String password;
    }
}