
package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.mock;

import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.BasketFileDAO;
import com.ufund.api.ufundapi.persistence.CupboardFileDAO;


/*
 * Unit Test class for CupboardDAO.
 * @author iz6341
 */

public class CupboardDaoTest {
    private CupboardFileDAO cupboardFileDAO;
    private ObjectMapper mockCupboard;
    private Need[] testNeeds;

    @BeforeEach
    public void setup() throws IOException {
        mockCupboard = new ObjectMapper();
        testNeeds = new Need[3];
        testNeeds[0] = new Need(1, "Apple", "Fruit", 1.5, 10);
        testNeeds[1] = new Need(2, "Milk", "Dairy", 4.0, 5);
        testNeeds[2] = new Need(3, "Orange", "Fruit", 2.5, 8);

        File tempFile = new File("anyfile.txt");
        mockCupboard.writeValue(tempFile, testNeeds);
        cupboardFileDAO = new CupboardFileDAO("anyfile.txt", mockCupboard);
        tempFile.deleteOnExit();
    }

    /**
     * Test - get needd
     */
    @Test
    public void testGetNeeds() {
        Need[] needs = cupboardFileDAO.getNeeds();

        assertEquals(testNeeds.length, needs.length);
        for (int i = 0; i < testNeeds.length; ++i)
            assertEquals(testNeeds[i].getName(), needs[i].getName());
    }

    /**
     * Test - find needs.
     */
    @Test
    public void testFindNeeds() {
        Need[] needs = cupboardFileDAO.findNeeds("App");
            assertEquals(1, needs.length);
            assertEquals(testNeeds[0].getName(), needs[0].getName());
    }

    /**
     * Test - get need.
     */
    @Test
    public void testGetNeed() {
        Need need = cupboardFileDAO.getNeed(1);
        assertEquals(testNeeds[0].getName(), need.getName());
    }

    /**
     * Test - get need. (Not found)
     */
    @Test
    public void testGetNeedNotFound() {
        Need need = cupboardFileDAO.getNeed(98);
        assertEquals(need,null);
    }

    /**
     * Test - delete need. (Not found)
     */
    @Test
    public void testDeleteNeed() throws IOException {
        boolean result = cupboardFileDAO.deleteNeed(1);
        assertTrue(result);
        assertEquals(2, cupboardFileDAO.getNeeds().length);
    }

    /**
     * Test - CreateNeed
     * @throws IOException
     */
    @Test
    public void testCreateNeed() throws IOException {
        Need newNeed = new Need(0, "Rice", "Grains", 10.0, 2);

        Need result = assertDoesNotThrow(() -> cupboardFileDAO.createNeed(newNeed),
                                "Unexpected exception thrown");

        assertNotNull(result);
        Need actual = cupboardFileDAO.getNeed(result.getId());
        assertEquals(actual.getId(),result.getId());
        assertEquals(actual.getName(),result.getName());
    }

    /**
     * Test - update hero.
     */
    @Test
    public void testUpdateNeed() {
        Need need = new Need(1, "Mango", "Fruit", 1.5, 10);

        Need result = assertDoesNotThrow(() -> cupboardFileDAO.updateNeed(need),
                                "Unexpected exception thrown");

        assertNotNull(result);
        Need actual = cupboardFileDAO.getNeed(result.getId());
        assertEquals(actual,result);
    }

    /**
     * Test - update hero (Not Found)
     */
    @Test
    public void testUpdateNeedNotFound() {
        Need need = new Need(5, "Watermelon", "Fruit", 1.5, 10);

        Need result = assertDoesNotThrow(() -> cupboardFileDAO.updateNeed(need),
                                                "Unexpected exception thrown");

        assertNull(result);
    }

    /**
     * Test - delete need (Not Found)
     */
    @Test
    public void testDeleteNeedNotFound() {
        boolean result = assertDoesNotThrow(() -> cupboardFileDAO.deleteNeed(50),
                                                "Unexpected exception thrown");

        assertEquals(result,false);
        assertEquals(testNeeds.length, cupboardFileDAO.getNeeds().length);
    }
}
