/**
 * Interface for User DAO class
 * @author qry3977
 */
package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.User;

public interface UserDAO {

    /**
     * Retrieves all users.
     * 
     * @return an array of all users, may be empty if no users exist
     * @throws IOException if there is an error reading from storage
     */
    User[] getUsers() throws IOException;

    /**
     * Retrieves a single user by their ID.
     * 
     * @param id the unique ID of the user
     * @return the {@link User} object if found, or null if not found
     * @throws IOException if there is an error reading from storage
     */
    User getUser(int id) throws IOException;

    /**
     * Finds a user by their username.
     * 
     * @param username the username to search for
     * @return the {@link User} object if found, or null if not found
     * @throws IOException if there is an error reading from storage
     */
    User findByUsername(String username) throws IOException;

    /**
     * Creates a new user and assigns a unique ID.
     * 
     * @param user the {@link User} object to create (ID will be ignored)
     * @return the newly created {@link User} with assigned ID
     * @throws IOException if there is an error saving the user
     */
    User createUser(User user) throws IOException;

    /**
     * Deletes a user by ID.
     * 
     * @param id the unique ID of the user to delete
     * @return true if the user existed and was deleted, false if the user was not found
     * @throws IOException if there is an error updating the storage
     */
    boolean deleteUser(int id) throws IOException;

    /**
     * Updates an existing user.
     * 
     * @param user the {@link User} object with updated information (must have valid ID)
     * @return the updated {@link User} object, or null if the user does not exist
     * @throws IOException if there is an error saving the user
     */
    User updateUser(User user) throws IOException;
}