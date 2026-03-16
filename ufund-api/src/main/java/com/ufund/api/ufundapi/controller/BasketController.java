package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import java.util.logging.Level;
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
import org.springframework.beans.factory.annotation.Qualifier;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.CupboardDAO;
import com.ufund.api.ufundapi.persistence.BasketDAO;

/**
 * Handles CupboardDAO requests
 * 
 * @author ars4041
 * @author iz6341
 */

@RestController
@RequestMapping("basket")
public class BasketController {
    private static final Logger LOG = Logger.getLogger(BasketController.class.getName());
    private final BasketDAO basketDAO;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param cupboardDao The {@link cupboardDao Need Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public BasketController(BasketDAO basketDao) {
        this.basketDAO = basketDao;
    }

   

    /**
     * Responds to the GET request for all {@linkplain Need need}
     * 
     * @return ResponseEntity with array of {@link Need need} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Need[]> getBasket(@PathVariable int userId) {
        LOG.info("GET /basket/" + userId);
        try {
            Need[] needs = basketDAO.getNeeds(userId);
            return new ResponseEntity<>(needs, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/{userId}")
    public ResponseEntity<Need> addToBasket(@PathVariable int userId, @RequestBody Need need) {
        LOG.info("POST /basket/" + userId + " " + need);
        try {
            Need addedNeed = basketDAO.addToBasket(userId, need);
            return new ResponseEntity<>(addedNeed, HttpStatus.CREATED);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{userId}/{needId}")
    public ResponseEntity<Void> removeFromBasket(@PathVariable int userId, @PathVariable int needId) {
        LOG.info("DELETE /basket/" + userId + "/" + needId);
        try {
            boolean deleted = basketDAO.removeFromBasket(userId, needId);
            if (deleted)
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}