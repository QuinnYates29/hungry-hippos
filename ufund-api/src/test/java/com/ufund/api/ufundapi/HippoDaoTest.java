
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
import com.ufund.api.ufundapi.model.Hippo;
import com.ufund.api.ufundapi.persistence.HippoFileDAO;
import com.ufund.api.ufundapi.persistence.HippoDAO;
import java.time.LocalDate;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;



/**
 * Unit tests for HippoFileDAO.
 * @author Ilia Zhdanov (iz6341)
 * @author Quinn Yates (qry3977)
 */
public class HippoDaoTest {
    private HippoFileDAO hippoFileDAO;
    private ObjectMapper mockHippo;
    private Hippo[] testHippos;

    @BeforeEach
    public void setup() throws IOException {
        mockHippo = new ObjectMapper();
        mockHippo.registerModule(new JavaTimeModule());
        testHippos = new Hippo[2];
        testHippos[0] = new Hippo(1,"Gloria","Hippopotamus amphibius","Female", LocalDate.of(2015, 5, 10), 1500.0, -1.2921, 36.8219);
        testHippos[1] = new Hippo(2,"Henry","Hippopotamus amphibius","Male", LocalDate.of(2010, 3, 15), 2000.0, -1.2921, 36.8219);  
        File tempFile = new File("anyfile2.txt");
        mockHippo.writeValue(tempFile, testHippos);
        hippoFileDAO = new HippoFileDAO("anyfile2.txt", mockHippo);
        tempFile.deleteOnExit();
    }


    /**
     * Test - get hippos.
     */
    @Test
    public void testGetHippos() {
        Hippo[] hippos = hippoFileDAO.getHippos();
        assertEquals(testHippos.length, hippos.length);
        for (int i = 0; i < testHippos.length; i++) {
            assertEquals(testHippos[i].getName(), hippos[i].getName());
        }
    }

    /**
     * Test - get hippo by id.
    */
    @Test
    public void testGetHippo() {
        Hippo hippo = hippoFileDAO.getHippo(1);
        assertNotNull(hippo);
        assertEquals("Gloria", hippo.getName());
        assertEquals("Hippopotamus amphibius", hippo.getSpecies());
        assertEquals(LocalDate.of(2015, 5, 10), hippo.getBirthdate());
    }

    /**
     * Test - create hippo.
     */
    @Test
    public void testCreateHippo() throws IOException {
        Hippo newHippo = new Hippo(0,"Hannah","Hippopotamus amphibius","Female", LocalDate.of(2018, 7, 20), 1200.0, -1.2921, 36.8219);
        Hippo result = assertDoesNotThrow(() -> hippoFileDAO.createHippo(newHippo), "Unexpected exception thrown");
        assertNotNull(result);
        assertEquals("Hannah", result.getName());
        assertEquals("Hippopotamus amphibius", result.getSpecies());
        assertTrue(result.getId() > 0);
        assertEquals(LocalDate.of(2018, 7, 20), result.getBirthdate());
        Hippo[] hippos = hippoFileDAO.getHippos();
        assertEquals(3, hippos.length);
    }

    /**
     * Test - update hippo.
     */
    @Test
    public void testUpdateHippo() throws IOException {
        Hippo updatedHippo = new Hippo(1,"Gloria","Hippopotamus amphibius","Female", LocalDate.of(2015, 5, 10), 1600.0, -1.2921, 36.8219);
        Hippo result = assertDoesNotThrow(() -> hippoFileDAO.updateHippo(updatedHippo), "Unexpected exception thrown");
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(1600.0, result.getWeight());
        Hippo hippo = hippoFileDAO.getHippo(1);
        assertEquals(1600.0, hippo.getWeight());
    }

    /**
     * Test - delete hippo.
     */
    @Test
    public void testDeleteHippo() throws IOException {
        boolean result = hippoFileDAO.deleteHippo(1);
        assertTrue(result);
        assertEquals(1, hippoFileDAO.getHippos().length);
        assertNull(hippoFileDAO.getHippo(1));
    }

    /**
     * Test - find hippo.
    */
    @Test
    public void testFindHippo() {
        Hippo[] results = hippoFileDAO.findHippos("Henry");
        assertNotNull(results);
        assertEquals(1, results.length);
        Hippo hippo = results[0];
        assertEquals("Henry", hippo.getName());
        assertEquals("Hippopotamus amphibius", hippo.getSpecies());
        assertEquals(LocalDate.of(2010, 3, 15), hippo.getBirthdate());
    }
}



    