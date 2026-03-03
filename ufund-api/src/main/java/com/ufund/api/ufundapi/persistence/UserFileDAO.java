/**
 * Implementation for UserDAO class
 * @author qry3977
 */
package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.User;

@Component
public class UserFileDAO implements UserDAO {

    private static final Logger LOG = Logger.getLogger(UserFileDAO.class.getName());
    Map<Integer,User> users;
    private ObjectMapper objectMapper;
    private static int nextId;

    public UserFileDAO(ObjectMapper objectMapper) throws IOException {
        this.objectMapper = objectMapper;
        load();
    }

    private void load() throws IOException {
        users = new HashMap<>();
        nextId = 1;
    }

    private User[] getUsersArray() {
        return users.values().toArray(new User[0]);
    }

    @Override
    public User[] getUsers() {
        return getUsersArray();
    }

    @Override
    public User getUser(int id) {
        return users.get(id);
    }

    @Override
    public User findByUsername(String username) {
        for (User user : users.values()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User createUser(User user) {
        User newUser = new User(nextId++, user.getUsername(), user.getPassword(), user.getRole());
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    @Override
    public boolean deleteUser(int id) {
        return users.remove(id) != null;
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            return null;
        }
        users.put(user.getId(), user);
        return user;
    }
}