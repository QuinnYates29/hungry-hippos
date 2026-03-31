/**
 * Controller class for managing Hippo data
 * Provides endpoints to fetch, create, update, and delete hippos
 * @author qry3977
 * @author Ilia
 */
package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.model.Hippo;
import com.ufund.api.ufundapi.persistence.HippoDAO;

@RestController
@RequestMapping("/hippos")
public class HippoController {

    private static final Logger LOG = Logger.getLogger(HippoController.class.getName());

    private final HippoDAO hippoDao;

    public HippoController(HippoDAO hippoDao) {
        this.hippoDao = hippoDao;
    }

    /**
     * Retrieves all hippos.
     * * @return array of all hippos
     */
    @GetMapping
    public ResponseEntity<Hippo[]> getAllHippos() {
        LOG.info("GET /hippos");
        try {
            Hippo[] hippos = hippoDao.getHippos();
            return ResponseEntity.ok(hippos);
        } catch (IOException e) {
            LOG.severe("Failed to read hippos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieves a single hippo by its ID.
     * * @param id the hippo ID
     * @return the hippo object or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Hippo> getHippo(@PathVariable int id) {
        LOG.info("GET /hippos/" + id);
        try {
            Hippo hippo = hippoDao.getHippo(id);
            if (hippo == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(hippo);
        } catch (IOException e) {
            LOG.severe("Failed to read hippo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Searches for hippos with names that contain the given text.
     * * @param name the text to search for
     * @return array of matching hippos
     */
    @GetMapping("/")
    public ResponseEntity<Hippo[]> searchHippos(@RequestParam String name) {
        LOG.info("GET /hippos/?name=" + name);
        try {
            Hippo[] matches = hippoDao.findHippos(name);
            return ResponseEntity.ok(matches);
        } catch (IOException e) {
            LOG.severe("Failed to search hippos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Creates a new hippo.
     * * @param hippo the hippo to create
     * @return the created hippo
     */
    @PostMapping
    public ResponseEntity<Hippo> createHippo(@RequestBody Hippo hippo) {
        LOG.info("POST /hippos " + hippo);
        try {
            Hippo created = hippoDao.createHippo(hippo);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IOException e) {
            LOG.severe("Failed to create hippo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Updates an existing hippo.
     * * @param hippo the updated hippo data
     * @return the updated hippo, or 404 if it didn't exist
     */
    @PutMapping
    public ResponseEntity<Hippo> updateHippo(@RequestBody Hippo hippo) {
        LOG.info("PUT /hippos " + hippo);
        try {
            Hippo updated = hippoDao.updateHippo(hippo);
            if (updated == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updated);
        } catch (IOException e) {
            LOG.severe("Failed to update hippo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Deletes a hippo by ID.
     * * @param id the ID of the hippo to delete
     * @return 200 OK if deleted, 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHippo(@PathVariable int id) {
        LOG.info("DELETE /hippos/" + id);
        try {
            boolean deleted = hippoDao.deleteHippo(id);
            if (deleted) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            LOG.severe("Failed to delete hippo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}