/**
 * Class representing the User Data Object
 * Used for login and user data persistence
 * @author Quinn Yates (qry3977)
 */
package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a user in the system.
 */
public class User {
    private static final Logger LOG = Logger.getLogger(User.class.getName());

    static final String STRING_FORMAT = "User [id=%d, username=%s, role=%s]";

    @JsonProperty("di") private int id;
    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password;
    @JsonProperty("role") private String role; // ADMIN or HELPER

    /**
     * Creates a User with the given information.
     * @param id The unique ID of the user
     * @param username The username for login
     * @param password The user's password
     * @param role The role of the user (ADMIN or HELPER)
     */
    public User(
        @JsonProperty("id") int id,
        @JsonProperty("username") String username,
        @JsonProperty("password") String password,
        @JsonProperty("role") String role
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Gets the user's ID.
     * 
     * @return the user ID
     */
    public int getId() { return id; }

    /**
     * Gets the username.
     * 
     * @return the username
     */
    public String getUsername() { return username; }

    /**
     * Gets the password.
     * 
     * @return the password
     */
    public String getPassword() { return password; }

    /**
     * Gets the role of the user.
     * 
     * @return the role (ADMIN or HELPER)
     */
    public String getRole() { return role; }

    /**
     * Sets a new username.
     * 
     * @param username the updated username
     */
    public void setUsername(String username) { 
        this.username = username; 
    }

    /**
     * Sets a new password.
     * 
     * @param password the updated password
     */
    public void setPassword(String password) { 
        this.password = password; 
    }

    /**
     * Sets the user's role.
     * 
     * @param role the updated role
     */
    public void setRole(String role) { 
        this.role = role; 
    }

    /**
     * Returns a readable string representation of the user.
     * The password is intentionally not included.
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, username, role);
    }
}