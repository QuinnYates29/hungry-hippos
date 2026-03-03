/**
 * Class representing the User Data Object
 * Used for login and user data persistence
 * @author Quinn Yates (qry3977)
 */
package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a user entity
 */
public class User {
    private static final Logger LOG = Logger.getLogger(User.class.getName());

    static final String STRING_FORMAT = "User [id=%d, username=%s, role=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password;
    @JsonProperty("role") private String role; // ADMIN or HELPER

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

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, username, role);
    }
}