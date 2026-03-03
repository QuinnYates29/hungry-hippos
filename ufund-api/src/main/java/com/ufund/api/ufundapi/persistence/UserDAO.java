/**
 * Interface for User DAO class
 * @author qry3977
 */
package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.User;

public interface UserDAO {

    User[] getUsers() throws IOException;

    User getUser(int id) throws IOException;

    User findByUsername(String username) throws IOException;

    User createUser(User user) throws IOException;

    boolean deleteUser(int id) throws IOException;

    User updateUser(User user) throws IOException;
}