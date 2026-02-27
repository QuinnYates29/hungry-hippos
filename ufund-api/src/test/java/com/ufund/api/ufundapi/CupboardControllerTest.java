package com.ufund.api.ufundapi;

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

    private CupboardController cupboardController;
    private CupboardDAO mockDao;

    @BeforeEach
    void setUp() {
        mockDao = Mockito.mock(CupboardDAO.class);
        cupboardController = new CupboardController(mockDao);
    }

    @Test
    void testGetNeeds() throws Exception {
        Need[] mockNeeds = new Need[1];
        mockNeeds[0] = new Need(1, "Tuna", "Food", 3.00, 10);

        when(mockDao.getNeeds()).thenReturn(mockNeeds);

        ResponseEntity<Need[]> response = cupboardController.getNeeds();
        Need[] result = response.getBody();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(result);
        assertEquals(1, result.length);           // size matches mock array
        assertEquals("Tuna", result[0].getName()); // check name
        assertEquals("Food", result[0].getType()); // check type
        assertEquals(3.00, result[0].getCost());
        assertEquals(10, result[0].getQuantity());
    }
}