package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.Need;

/**
 * Defines the interface for Need object persistence
 * 
 * @author iz6341
 * @author ars4041
 */

public interface BasketDAO{

    /**
    * Adds a need to the user's basket.
    * @param userId The ID of the user.
    */
    Need addToBasket(int userId, Need need) throws IOException;
    
    /**
     * Retrieves all items in the basket for a specific user.
     * * @param userId The ID of the user
     * @return Array of {@link Need} objects; empty array if user has no basket
     * @throws IOException if data access fails
     */
    Need[] getNeeds(int userId) throws IOException;

    /**
     * Removes a specific item from a user's basket.
     * @param userId The ID of the user
     * @param needId The ID of the item to remove
     * @return true if the item was removed, false otherwise
     * @throws IOException if saving fails
     */
    boolean removeFromBasket(int userId, int needId) throws IOException;

    /**
     * Removes all items from a user's basket.
     * @param userId The ID of the user
     * @throws IOException if saving fails
     */
    void clearBasket(int userId) throws IOException;
}