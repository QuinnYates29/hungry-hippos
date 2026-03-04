package com.ufund.api.ufundapi;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
public void testGetNeed_Ok() throws IOException {
    Need need = new Need(1, "Tuna", "Food", 3.00, 10);
    when(mockDao.getNeed(1)).thenReturn(need);

    ResponseEntity<Need> response = controller.getNeed(1);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(need, response.getBody());
}

@Test
public void testGetNeed_NotFound() throws IOException {
    when(mockDao.getNeed(1)).thenReturn(null);

    ResponseEntity<Need> response = controller.getNeed(1);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
}

@Test
public void testGetNeed_InternalServerError() throws IOException {
    when(mockDao.getNeed(1)).thenThrow(new IOException());

    ResponseEntity<Need> response = controller.getNeed(1);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
}
}