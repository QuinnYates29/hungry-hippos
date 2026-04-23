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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.BasketDAO;
import com.ufund.api.ufundapi.persistence.CupboardDAO;

/**
 * Handles the REST API requests for the Funding Basket.
 * Allows users to view, add, and remove items from their specific basket.
 * @author ars4041
 * @author iz6341
 */

@RestController
@RequestMapping("basket")
public class BasketController {
    private static final Logger LOG = Logger.getLogger(BasketController.class.getName());
    private final BasketDAO basketDAO;
    private final CupboardDAO cupboardDAO;

    /**
     * Creates a REST API controller to respond to basket requests.
     * * @param basketDao The {@link BasketDAO} used to perform CRUD operations on the basket.
     */
    public BasketController(BasketDAO basketDao, CupboardDAO cupboardDao) {
        this.basketDAO = basketDao;
        this.cupboardDAO = cupboardDao;
    }

   

    /**
     * Responds to the GET request for a specific user's Funding Basket.
     * @param userId The ID of the user whose basket is being retrieved
     * @return ResponseEntity with an array of {@link Need} objects and HTTP status OK,
     * otherwise, INTERNAL_SERVER_ERROR
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Need[]> getBasket(@PathVariable int userId) {
        LOG.info("GET /basket/" + userId);
        try {
            Need[] needs = basketDAO.getNeeds(userId);
            if(needs!=null){
                return new ResponseEntity<>(needs, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(needs, HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Adds a {@link Need} to a specific user's basket.
     * @param userId The ID of the user adding the item
     * @param need The {@link Need} object to be added
     * @return ResponseEntity with the added {@link Need} and HTTP status CREATED,
     * otherwise, INTERNAL_SERVER_ERROR
     */
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

    /**
     * Removes a specific {@link Need} from a user's basket.
     * @param userId The ID of the user removing the item
     * @param needId The ID of the {@link Need} to be removed
     * @return ResponseEntity with HTTP status OK if deleted,
     * NOT_FOUND if the item or basket didn't exist,
     * otherwise, INTERNAL_SERVER_ERROR
     */
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


    /**
     * Checks out a user's basket.
     * Deletes each item in the basket from the main cupboard and then clears the basket.
     * @param userId The ID of the user checking out
     * @return HTTP OK if successful, otherwise INTERNAL_SERVER_ERROR
     */
    @PostMapping("/checkout/{userId}")
    public ResponseEntity<Void> checkout(@PathVariable int userId) {
        LOG.info("POST /basket/checkout/" + userId);
        try {
            //get all items currently in the user's basket
            Need[] basketitems = basketDAO.getNeeds(userId);

            //delete each from the main cupboard
            for (Need item : basketitems) {
                cupboardDAO.deleteNeed(item.getId());
            }

            //clear the user's basket
            basketDAO.clearBasket(userId);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}