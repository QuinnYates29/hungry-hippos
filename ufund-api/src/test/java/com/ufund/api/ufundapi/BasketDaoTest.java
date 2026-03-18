package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
    private Map<Integer, Need[]> testBaskets;

    @BeforeEach
    public void setup() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        Map<Integer, Need[]> mockData = new HashMap<>();
        Need[] needs = new Need[1];
        needs[0]=new Need(1, "Grass", "Food", 0, 10);
        mockData.put(100, needs);

        when(mockObjectMapper.readValue(any(File.class), any(TypeReference.class)))
        .thenReturn(mockData);
        basketFileDAO = new BasketFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testAdd() throws IOException {
        Need newNeed = new Need(2, "Apple", "Fruit", 5.0, 1);
    
   
        Need result = assertDoesNotThrow(() -> basketFileDAO.addToBasket(100, newNeed),
                                "Unexpected exception thrown");
        assertNotNull(result);
        assertEquals("Apple", result.getName());
        
    
    }
    
}
