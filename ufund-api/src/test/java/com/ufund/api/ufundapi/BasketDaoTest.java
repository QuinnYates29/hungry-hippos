package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.BasketFileDAO;


public class BasketDaoTest {
    private BasketFileDAO basketFileDAO;
    private ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setup() throws IOException {
        mockObjectMapper = new ObjectMapper();
        
        //creeate a map
        Map<Integer, Map<Integer, Need>> mockData = new HashMap<>();

        Map<Integer, Need> user3Basket = new HashMap<>();
        Need grass = new Need(1, "Orange", "Food", 0, 10);

        user3Basket.put(1, grass);

        mockData.put(3, user3Basket);

        File tempFile = new File("doesnt_matter.txt");
        mockObjectMapper.writeValue(tempFile, mockData);
        basketFileDAO = new BasketFileDAO("doesnt_matter.txt", mockObjectMapper);
        tempFile.deleteOnExit();
    }

    /**
     * Test- add to basket
     * @author iz6341
     */
    @Test
    public void testAddToBasket() throws IOException {
        Need newNeed = new Need(2, "Apple", "Fruit", 5.0, 1);
    
   
        Need result = assertDoesNotThrow(() -> basketFileDAO.addToBasket(3, newNeed),
                     "Unexpected exception thrown");
        assertNotNull(result);
        assertEquals("Apple", result.getName());
        assertEquals(2, basketFileDAO.getNeeds(3).length);
    }

    /**
     * Test - add to basket
     * @author iz6341
     */
    @Test
    public void testRemoveFromBasket() throws IOException {
        boolean removed = assertDoesNotThrow(() -> basketFileDAO.removeFromBasket(3, 1),
                        "Unexpected exception thrown");

        assertTrue(removed);
        assertEquals(0, basketFileDAO.getNeeds(3).length);
    }


    /**
     * Test-get needs
     */
    @Test
    public void testGetNeeds() throws IOException {
        Need[] needs = basketFileDAO.getNeeds(3);
        assertEquals(1, needs.length);
        assertEquals("Orange", needs[0].getName());
    }

    /**
     * Test- clear
     */
    @Test
    public void testClearBasket() throws IOException {
        basketFileDAO.clearBasket(3);
        assertEquals(0, basketFileDAO.getNeeds(3).length);
    } 

    /**
     * Test- empty remove
     */
    @Test
    public void testRemoveFromBasket_Empty() throws IOException {
       boolean result = assertDoesNotThrow(() -> basketFileDAO.removeFromBasket(3, 99),
                        "Unexpected exception thrown");
        assertEquals(false, result);
        assertEquals(1, basketFileDAO.getNeeds(3).length);
    }
}
