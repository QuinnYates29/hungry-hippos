package com.ufund.api.ufundapi;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.persistence.UserFileDAO;


/**
 * Class of unit test for userDao.
 * @author iz6341
 */



public class UserDaoTest {
    private ObjectMapper mockUser;
    private User[] testUsers;
    private UserFileDAO userDao;

    @BeforeEach
    public void setup() throws IOException {
        mockUser = new ObjectMapper();
        testUsers = new User[3];
        testUsers[0]=new User(1,"admin","123","ADMIN");
        testUsers[1]=new User(2,"ilya","ty","HELPER");
        testUsers[2]=new User(3,"quinn","1234","HELPER");

        File tempFile = new File("something.txt");
        mockUser.writeValue(tempFile, testUsers);
        userDao = new UserFileDAO("something.txt", mockUser);
        tempFile.deleteOnExit();
    }

    /**
     * Test get users.
     */
    @Test
    public void testGetUsers(){
        User[] users = userDao.getUsers();
        assertEquals(testUsers.length, users.length);
        for (int i = 0; i < testUsers.length; ++i)
            assertEquals(testUsers[i].getUsername(), users[i].getUsername());
    }

    /**
     * Test - get user.
     */
    @Test
    public void testGetUser(){
        User user=userDao.getUser(1);
        assertEquals(testUsers[0].getUsername(), user.getUsername());
    }


    /**
     * Test - create user.
     * @throws IOException
     */
    @Test
    public void testCreateUser() throws IOException{
        User newuser = new User(4, "Swayman", "uiy", "HELPER");
        int originallen=userDao.getUsers().length;
        User result = assertDoesNotThrow(() -> userDao.createUser(newuser),
                                "Unexpected exception thrown");

        assertNotNull(result);
        assertEquals(originallen+1, userDao.getUsers().length);
        User actual = userDao.getUser(result.getId());
        assertEquals(actual.getId(), result.getId());
        assertEquals(actual.getUsername(), result.getUsername());
    }
    /**
     * Test - delete user.
     * @throws IOException
     */
    @Test
    public void testDelete() throws IOException{
        int originallen=userDao.getUsers().length;
        boolean result= assertDoesNotThrow(() -> userDao.deleteUser(2),
                                "Unexpected exception thrown");
        assertEquals(result,true);
        assertEquals(userDao.getUsers().length,originallen-1);   
    }
    /**
     * Test - delete user (False).
     * @throws IOException
     */
    @Test
    public void testDeleteNotFound() throws IOException{
        int originallen=userDao.getUsers().length;
        boolean result= assertDoesNotThrow(() -> userDao.deleteUser(50),
                                "Unexpected exception thrown");
        assertEquals(result,false);
        assertEquals(userDao.getUsers().length,originallen);   
    }

    /**
     * Test - find by username
     */
    @Test
    public void testFindByUsername(){
        User find = userDao.findByUsername("ilya");
        assertNotNull(find);
        assertEquals("ilya",find.getUsername());
    }

    /**
     * Test - find by username(Null)
     */
    @Test
    public void testFindByUsernameNotFound(){
        User find = userDao.findByUsername("Regan");
        assertEquals(find, null);
    }
    /**
     * Test - update user.
     */
    @Test
    public void testUpdateUser(){
        User update = new User(2,"ilya","578","HELPER");
        User result= assertDoesNotThrow(() -> userDao.updateUser(update),
                                "Unexpected exception thrown");
        assertNotNull(result);
        User actual = userDao.getUser(update.getId());
        assertEquals(update.getPassword(), actual.getPassword());
    }
    /**
     * Test - create helper.
     */
    @Test
    public void testCreateHelper() throws IOException {
        int originalen=userDao.getUsers().length;
        User result= assertDoesNotThrow(() -> userDao.createHelper("aidan", "789"),
                                "Unexpected exception thrown");
        assertNotNull(result);
        assertEquals(originalen+1, userDao.getUsers().length);
        assertEquals("HELPER", result.getRole());
    }






    
}
