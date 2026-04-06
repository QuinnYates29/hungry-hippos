package com.ufund.api.ufundapi;
import com.ufund.api.ufundapi.controller.HippoController;
import java.io.IOException;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.ufund.api.ufundapi.model.Hippo;
import com.ufund.api.ufundapi.persistence.HippoDAO;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.time.LocalDate;




/**
 * Unit tests for HippoController using Mockito to mock the HippoDAO.
 * @author Ilia Zhdanov (iz6341)
 * @author Quinn Yates (qry3977)
 */
public class HippoControllerTest {
    private HippoController hippoController;
    private HippoDAO mockDao;


    @BeforeEach
    public void setUp() {
        mockDao = Mockito.mock(HippoDAO.class);
        hippoController = new HippoController(mockDao);
    }

    /**
     * Tests that getAllHippos returns the correct list of hippos when the DAO returns data.
     */
    @Test
    public void testGetAllHippos_Ok() throws IOException {
        Hippo[] hippos = new Hippo[1];
        hippos[0] = new Hippo(1,"Gloria","Hippopotamus amphibius","Female", LocalDate.of(2015, 5, 10), 1500.0, -1.2921, 36.8219);
        when(mockDao.getHippos()).thenReturn(hippos);
        ResponseEntity<Hippo[]> response = hippoController.getAllHippos();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertEquals("Gloria", response.getBody()[0].getName());
        assertEquals("Hippopotamus amphibius", response.getBody()[0].getSpecies());
        assertEquals(LocalDate.of(2015, 5, 10), response.getBody()[0].getBirthdate());
    }

    /**
     * Test get hippos Internal Server Error.
     */
    @Test
    public void testGetAllHippos_InternalServerError() throws IOException {
        when(mockDao.getHippos()).thenThrow(new IOException());

        ResponseEntity<Hippo[]> response = hippoController.getAllHippos();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Tests that getHippo OK.
     */
    @Test
    public void testGetHippo_Ok() throws IOException {
        Hippo hippo = new Hippo(1,"Gloria","Hippopotamus amphibius","Female", LocalDate.of(2015, 5, 10), 1500.0, -1.2921, 36.8219);
        when(mockDao.getHippo(1)).thenReturn(hippo);

        ResponseEntity<Hippo> response = hippoController.getHippo(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Gloria", response.getBody().getName());
        assertEquals("Hippopotamus amphibius", response.getBody().getSpecies());
        assertEquals(LocalDate.of(2015, 5, 10), response.getBody().getBirthdate());
    }

    /**
     * Tests that getHippo returns NOT_FOUND when the hippo does not exist.
     */
    @Test
    public void testGetHippo_NotFound() throws IOException {
        when(mockDao.getHippo(100)).thenReturn(null);
        ResponseEntity<Hippo> response = hippoController.getHippo(100);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests getHippo returns INTERNAL_SERVER_ERROR.
     */
    @Test
    public void testGetHippo_InternalServerError() throws IOException {
        when(mockDao.getHippo(1)).thenThrow(new IOException());
        ResponseEntity<Hippo> response = hippoController.getHippo(1);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Tests searchHippos returns OK for a partial name match.
     */
    @Test
    public void testSearchHippos_Ok() throws IOException {
        Hippo[] hippos = new Hippo[1];
        hippos[0] = new Hippo(1,"Gloria","Hippopotamus amphibius","Female", LocalDate.of(2015, 5, 10), 1500.0, -1.2921, 36.8219);
        when(mockDao.findHippos("Glor")).thenReturn(hippos);
        ResponseEntity<Hippo[]> response = hippoController.searchHippos("Glor");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertEquals("Gloria", response.getBody()[0].getName());
    }


    /**
     * Tests searchHippos returns INTERNAL_SERVER_ERROR.
     */
    @Test
    public void testSearchHippos_InternalServerError() throws IOException {
        when(mockDao.findHippos("Glor")).thenThrow(new IOException());
        ResponseEntity<Hippo[]> response = hippoController.searchHippos("Glor");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Test createHippo returns CREATED when a new hippo is created successfully.
     */
    @Test
    public void testCreateHippo_Created() throws IOException {
        Hippo hippoToCreate = new Hippo(0,"Gloria","Hippopotamus amphibius","Female", LocalDate.of(2015, 5, 10), 1500.0, -1.2921, 36.8219);
        Hippo createdHippo = new Hippo(1,"Gloria","Hippopotamus amphibius","Female", LocalDate.of(2015, 5, 10), 1500.0, -1.2921, 36.8219);
        when(mockDao.createHippo(hippoToCreate)).thenReturn(createdHippo);
        ResponseEntity<Hippo> response = hippoController.createHippo(hippoToCreate);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("Gloria", response.getBody().getName());
    }

    /**
     * Tests createHippo returns INTERNAL_SERVER_ERROR when an IOException occurs.
     */
    @Test
    public void testCreateHippo_InternalServerError() throws IOException {
        Hippo hippoToCreate = new Hippo(0,"Gloria","Hippopotamus amphibius","Female", LocalDate.of(2015, 5, 10), 1500.0, -1.2921, 36.8219);
        when(mockDao.createHippo(hippoToCreate)).thenThrow(new IOException());
        ResponseEntity<Hippo> response = hippoController.createHippo(hippoToCreate);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Tests updateHippo returns OK when an existing hippo is updated successfully.
     */
    @Test
    public void testUpdateHippo_Ok() throws IOException {
        Hippo hippoToUpdate = new Hippo(1,"Gloria","Hippopotamus amphibius","Female", LocalDate.of(2015, 5, 10), 1500.0, -1.2921, 36.8219);
        when(mockDao.updateHippo(hippoToUpdate)).thenReturn(hippoToUpdate);
        ResponseEntity<Hippo> response = hippoController.updateHippo(hippoToUpdate);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("Gloria", response.getBody().getName());
    }

    /**
     * Tests updateHippo returns NOT_FOUND when trying to update a non-existent hippo.
     */
    @Test
    public void testUpdateHippo_NotFound() throws IOException {
        Hippo hippoToUpdate = new Hippo(100,"Gloria","Hippopotamus amphibius","Female", LocalDate.of(2015, 5, 10), 1500.0, -1.2921, 36.8219);
        when(mockDao.updateHippo(hippoToUpdate)).thenReturn(null);
        ResponseEntity<Hippo> response = hippoController.updateHippo(hippoToUpdate);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    /**
     * Tests updateHippo returns INTERNAL_SERVER_ERROR when an IOException occurs.
     */
    @Test
    public void testUpdateHippo_InternalServerError() throws IOException {
        Hippo hippoToUpdate = new Hippo(1,"Gloria","Hippopotamus amphibius","Female", LocalDate.of(2015, 5, 10), 1500.0, -1.2921, 36.8219);
        when(mockDao.updateHippo(hippoToUpdate)).thenThrow(new IOException());
        ResponseEntity<Hippo> response = hippoController.updateHippo(hippoToUpdate);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Tests deleteHippo returns OK when an existing hippo is deleted successfully.
     */
    @Test
    public void testDeleteHippo_Ok() throws IOException {
        when(mockDao.deleteHippo(1)).thenReturn(true);
        ResponseEntity<Void> response = hippoController.deleteHippo(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Tests deleteHippo returns NOT_FOUND when trying to delete a non-existent hippo.
     */
    @Test
    public void testDeleteHippo_NotFound() throws IOException {
        when(mockDao.deleteHippo(100)).thenReturn(false);
        ResponseEntity<Void> response = hippoController.deleteHippo(100);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    /**
     * Tests deleteHippo returns INTERNAL_SERVER_ERROR when an IOException occurs.
     */
    @Test
    public void testDeleteHippo_InternalServerError() throws IOException {
        when(mockDao.deleteHippo(1)).thenThrow(new IOException());
        ResponseEntity<Void> response = hippoController.deleteHippo(1);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}





        