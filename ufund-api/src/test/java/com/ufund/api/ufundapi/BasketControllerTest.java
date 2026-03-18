/**
 * Test file for BasketController.java class
 * 
 * @author iz6341
 * 
 */

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

import com.ufund.api.ufundapi.controller.CupboardController;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.CupboardDAO;
import com.ufund.api.ufundapi.persistence.BasketDAO;
import com.ufund.api.ufundapi.controller.BasketController;


class BasketControllerTest {

    private BasketController controller;
    private BasketDAO mockDaoBasket;
    private CupboardDAO mockDaoCupboard;

    @BeforeEach
    void setUp() {
        mockDaoBasket = Mockito.mock(BasketDAO.class);
        mockDaoCupboard = Mockito.mock(CupboardDAO.class);
        controller = new BasketController(mockDaoBasket, mockDaoCupboard);
    }

    /**
     * Test get basket (Status OK) from the controller.
     * @author iz6341
     */
    @Test
    public void testGetBasket_Ok() throws Exception {
        Need[] mockNeeds = { 
        new Need(1, "Grass", "Food", 0, 10), 
        new Need(2, "Water", "Drink", 0, 20) 
        };
        int userId=2;

        when(mockDaoBasket.getNeeds(userId)).thenReturn(mockNeeds);
        ResponseEntity<Need[]> response = controller.getBasket(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Grass", response.getBody()[0].getName());
        assertEquals("Water", response.getBody()[1].getName());
    }

    /**
     * Test get basket (Status NOT_FOUND) from the controller.
     * @author iz6341
     */
    @Test
    public void testGetBasket_NotFound() throws IOException {
        int userId=2;
        when(mockDaoBasket.getNeeds(userId)).thenReturn(null);

        ResponseEntity<Need[]> response = controller.getBasket(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test get basket (Status INTERNAL_SERVER_ERROR) from the controller.
     * @author iz6341
     */
    @Test
    public void testGetBasket_InternalServerError() throws IOException {
        int userId=2;
        when(mockDaoBasket.getNeeds(userId)).thenThrow(new IOException());
        ResponseEntity<Need[]> response = controller.getBasket(userId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Test removeFromBasket (Status OK) from controller.
     * @author iz6341
     */
    @Test
    public void testRemoveFromBasket_Ok() throws IOException {
        int userId=2;
        int needId=2;
        when(mockDaoBasket.removeFromBasket(userId, needId)).thenReturn(true);
        ResponseEntity<Void> response = controller.removeFromBasket(userId,needId);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    /**
     * Test removeFromBasket (Status NOT_FOUND) from controller.
     * @author iz6341
     */
    @Test
    public void testRemoveFromBasket_NotFound() throws IOException {
        int userId=2;
        int needId=2;
        when(mockDaoBasket.removeFromBasket(userId, needId)).thenReturn(false);
        ResponseEntity<Void> response = controller.removeFromBasket(userId,needId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test removeFromBasket (Status INTERNAL_SERVER_ERROR) from controller.
     * @author iz6341
     */
    @Test
    public void testRemoveFrom_InternalServerError() throws IOException {
        int userId=2;
        int needId=2;
        when(mockDaoBasket.removeFromBasket(userId, needId)).thenThrow(new IOException());
        ResponseEntity<Void> response = controller.removeFromBasket(userId,needId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    /**
     * Test addToBasket (Status Created) from controller.
     * @author iz6341
     */
    @Test
    public void testAddToBasket_Created() throws IOException {
        int userId=2;
        Need newneed= new Need(1, "Grass", "Food", 0, 10);
        when(mockDaoBasket.addToBasket(userId, newneed)).thenReturn(newneed);
        ResponseEntity<Need> response= controller.addToBasket(userId, newneed);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Grass", response.getBody().getName());
    }


    /**
     * Test addToBasket (Status INTERNAL_SERVER_ERROR) from controller.
     * @author iz6341
     */
    @Test
    public void testAddtoBasket_InternalServerError() throws IOException {
        int userId=2;
        Need newneed= new Need(1, "Grass", "Food", 0, 10);
        when(mockDaoBasket.addToBasket(userId, newneed)).thenThrow(new IOException());
        ResponseEntity<Need> response = controller.addToBasket(userId, newneed);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());  
    }

    /**
     * Test checkout (Status OK) from controller.
     * @author iz6341
     */
    @Test
    public void testCheckout_Ok() throws IOException {
        int userId = 2;
        Need[] items = { new Need(1, "Apple", "Fruit", 0, 10) };
        when(mockDaoBasket.getNeeds(userId)).thenReturn(items);
        ResponseEntity<Void> response = controller.checkout(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Test checkout (Status INTERNAL_SERVER_ERROR) from controller.
     * @author iz6341
     */
    @Test
    public void testCheckout_InternalServerError() throws IOException {
        int userId = 2;
        when(mockDaoBasket.getNeeds(userId)).thenThrow(new IOException());
        ResponseEntity<Void> response = controller.checkout(userId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}