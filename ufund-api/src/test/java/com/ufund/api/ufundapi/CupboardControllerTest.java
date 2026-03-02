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

class CupboardControllerTest {

    private CupboardController controller;
    private CupboardDAO mockDao;

    @BeforeEach
    void setUp() {
        mockDao = Mockito.mock(CupboardDAO.class);
        controller = new CupboardController(mockDao);
    }

    // =========================
    // Tests for getNeeds()
    // =========================

    @Test
    void testGetNeeds() throws Exception {
        Need[] mockNeeds = new Need[1];
        mockNeeds[0] = new Need(1, "Tuna", "Food", 3.00, 10);

        when(mockDao.getNeeds()).thenReturn(mockNeeds);

        ResponseEntity<Need[]> response = controller.getNeeds();
        Need[] result = response.getBody();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(result);
        assertEquals(1, result.length);           // size matches mock array
        assertEquals("Tuna", result[0].getName()); // check name
        assertEquals("Food", result[0].getType()); // check type
        assertEquals(3.00, result[0].getCost());
        assertEquals(10, result[0].getQuantity());
    }

    @Test
    public void testGetNeeds_Ok() throws IOException {
        Need[] needs = { new Need(1, "Grass", "Food", 0, 10) };
        when(mockDao.getNeeds()).thenReturn(needs);

        ResponseEntity<Need[]> response = controller.getNeeds();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(needs, response.getBody());
    }

    @Test
    public void testGetNeeds_NotFound() throws IOException {
        when(mockDao.getNeeds()).thenReturn(null);

        ResponseEntity<Need[]> response = controller.getNeeds();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetNeeds_InternalServerError() throws IOException {
        when(mockDao.getNeeds()).thenThrow(new IOException());

        ResponseEntity<Need[]> response = controller.getNeeds();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // =========================
    // Tests for deleteNeed()
    // =========================

    @Test
    public void testDeleteNeed_Ok() throws IOException {
        when(mockDao.deleteNeed(1)).thenReturn(true);

        ResponseEntity<Need> response = controller.deleteNeed(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteNeed_NotFound() throws IOException {
        when(mockDao.deleteNeed(1)).thenReturn(false);

        ResponseEntity<Need> response = controller.deleteNeed(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteNeed_InternalServerError() throws IOException {
        when(mockDao.deleteNeed(1)).thenThrow(new IOException());

        ResponseEntity<Need> response = controller.deleteNeed(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // =========================
    // Tests for searchNeeds():
    // =========================
    @Test
    public void testSearchNeedsFullName_Ok() throws IOException {
        Need[] mockNeeds = { 
        new Need(1, "Grass", "Food", 0, 10), 
        new Need(2, "Water", "Drink", 0, 20) 
        };
        when(mockDao.getNeeds()).thenReturn(mockNeeds);
        ResponseEntity<Need[]> response = controller.searchNeeds("Grass");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertEquals("Grass", response.getBody()[0].getName());
    }

    @Test
    public void testSearchNeedsPartialName_Ok() throws IOException {
        Need[] mockNeeds = { 
            new Need(1, "Grass", "Food", 0, 10), 
            new Need(2, "Water", "Drink", 0, 20) 
        };
        when(mockDao.getNeeds()).thenReturn(mockNeeds);
        ResponseEntity<Need[]> response = controller.searchNeeds("wat");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertEquals("Water", response.getBody()[0].getName());
    }
    
    @Test
    public void testSearchNeeds_NotFound() throws IOException {
        Need[] mockNeeds = {
            new Need(1, "Grass", "Food", 0, 10),
            new Need(2, "Water", "Drink", 0, 20)
        };
        when(mockDao.getNeeds()).thenReturn(mockNeeds);
        ResponseEntity<Need[]> response = controller.searchNeeds("Cookie");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().length);
    }

    @Test
    public void testSearchNeeds_InternalServerError() throws IOException {
        when(mockDao.getNeeds()).thenThrow(new IOException());
        ResponseEntity<Need[]> response = controller.searchNeeds("Grass");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}