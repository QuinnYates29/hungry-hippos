/**
 * Implementation for UserDAO class
 * javadocs comments defined in interface
 * @author team 1a
 */
package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.User;

/**
 * Implements JSON file-based persistence for Users.
 * Uses an in-memory TreeMap as a cache, and saves/loads users
 * to a JSON file so that data persists across restarts.
 * This is similar to the CupboardFileDAO pattern used for Needs.
*/
@Component
public class UserFileDAO implements UserDAO {

    private static final Logger LOG = Logger.getLogger(UserFileDAO.class.getName());

    /** In-memory cache of users keyed by user ID */
    Map<Integer, User> users;

    /** JSON object mapper for serialization and deserialization */
    private ObjectMapper objectMapper;

    /** Next ID to assign for a new user */
    private static int nextId;

    /** JSON file path */
    private String filename;

    /**
     * Creates a UserFileDAO with a JSON file and ObjectMapper.
     * 
     * @param filename path to the JSON file for storing users
     * @param objectMapper JSON serializer/deserializer
     * @throws IOException if the file cannot be read
     */
    public UserFileDAO(@Value("${users.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load(); // Load existing users from file
    }

    /**
     * Generate the next unique ID for a user.
     * @return next ID
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Returns an array of all users.
     * @return array of users
     */
    private User[] getUsersArray() {
        return users.values().toArray(new User[0]);
    }

    /**
     * Saves the users map to the JSON file.
     * @return true if save was successful
     * @throws IOException if file cannot be written
     */
    private boolean save() throws IOException {
        User[] userArray = getUsersArray();
        objectMapper.writeValue(new File(filename), userArray);
        return true;
    }

    /**
     * Loads users from the JSON file into memory.
     * Also sets nextId to one greater than the max ID found.
     * @return true if load was successful
     * @throws IOException if file cannot be read
     */
    private boolean load() throws IOException {
        users = new TreeMap<>();
        nextId = 0;

        File file = new File(filename);
        if (!file.exists() || file.length() == 0) {
            LOG.info("Users file missing or empty. Starting with empty users list.");
            return true; // empty map is fine
        }

        User[] userArray = objectMapper.readValue(file, User[].class);
        for (User user : userArray) {
            users.put(user.getId(), user);
            if (user.getId() > nextId) nextId = user.getId();
        }
        ++nextId; // make nextId one greater than max
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public User[] getUsers() {
        synchronized(users) {
            return getUsersArray();
        }
    }

    /** {@inheritDoc} */
    @Override
    public User getUser(int id) {
        synchronized(users) {
            return users.get(id);
        }
    }

    /** {@inheritDoc} */
    @Override
    public User findByUsername(String username) {
        synchronized(users) {
            for (User user : users.values()) {
                if (user.getUsername().equals(username)) {
                    return user;
                }
            }
            return null;
        }
    }

    /** {@inheritDoc} */
    @Override
    public User createUser(User user) throws IOException {
        synchronized(users) {
            User newUser = new User(nextId(), user.getUsername(), user.getPassword(), user.getRole());
            users.put(newUser.getId(), newUser);
            save();
            return newUser;
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean deleteUser(int id) throws IOException {
        synchronized(users) {
            if (users.containsKey(id)) {
                users.remove(id);
                save();
                return true;
            }
            return false;
        }
    }

    /** {@inheritDoc} */
    @Override
    public User updateUser(User user) throws IOException {
        synchronized(users) {
            if (!users.containsKey(user.getId())) return null;
            users.put(user.getId(), user);
            save();
            return user;
        }
    }
}