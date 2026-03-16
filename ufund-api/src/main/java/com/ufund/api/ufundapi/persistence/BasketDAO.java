package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.Need;

/**
 * Defines the interface for Need object persistence
 * 
 * @author iz6341
 */

public interface BasketDAO{
    Need addToBasket(int userId, Need need) throws IOException;
    Need[] getNeeds(int userId) throws IOException;
    boolean removeFromBasket(int userId, int needId) throws IOException;
}