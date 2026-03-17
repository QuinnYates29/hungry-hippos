package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;

/**
 * Implements the {@link BasketDAO} interface for file-based persistence.
 * This class handles the storage and retrieval of specific baskets, 
 * mapping User IDs to a collection of {@link Need} objects.
 * @author ars4041
 * @author iz6341
 */

@Component
public class BasketFileDAO implements BasketDAO{
    private static final Logger LOG = Logger.getLogger(BasketFileDAO.class.getName());
    //a nested map structure
    private Map<Integer, Map<Integer, Need>> userBaskets;
    private final ObjectMapper objectMapper;
    private final String filename;

    /**
     * Creates a Basket File Data Object.
     * @param filename Path to the file where baskets are stored
     * @param objectMapper Jackson provider for JSON serialization/deserialization
     * @throws IOException if the file cannot be read or initialized
     */

    public BasketFileDAO(@Value("${basket.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }



    /**
     * Saves the current state of all user baskets to the file.
     * @return true if the save was successful
     * @throws IOException if the file cannot be written to
     */
    private boolean save() throws IOException {
        objectMapper.writeValue(new File(filename), userBaskets);
        return true;
    }

    /**
     * Loads user baskets from the JSON file into the baskets map.
     * Initializes an empty TreeMap if the file is missing or empty.
     * @return true if loading was successful
     * @throws IOException if there is an error during file reading
     */
    private boolean load() throws IOException {
        File file = new File(filename);

        userBaskets = new TreeMap<>();
        if (!file.exists() || file.length() == 0) {
            LOG.info("Basket file is missing or empty. Empty basket.");
            return true;
        }

        
        userBaskets = objectMapper.readValue(file, new TypeReference<>() {});

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Need[] getNeeds(int userId) throws IOException {
        synchronized (userBaskets) {
            Map<Integer, Need>currentBasket = userBaskets.get(userId);
            if (currentBasket == null) {
                return new Need[0];
            }
            return currentBasket.values().toArray(new Need[0]);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Need addToBasket(int userId, Need need) throws IOException {
        synchronized (userBaskets) {
            //get user's basket
            Map<Integer, Need> currentBasket = userBaskets.get(userId);
            if(currentBasket==null){
                currentBasket = new TreeMap<>();
                userBaskets.put(userId, currentBasket);
            }

            //put need to the map
            currentBasket.put(need.getId(), need);

            save();
            return currentBasket.get(need.getId());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeFromBasket(int userId, int needId) throws IOException {
        synchronized (userBaskets) {
            Map<Integer, Need> myBasket = userBaskets.get(userId);
            if (myBasket != null && myBasket.containsKey(needId)) {
                myBasket.remove(needId);
                return save();
            }
            return false;
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void clearBasket(int userId) throws IOException {
        synchronized (userBaskets) {
            if (userBaskets.containsKey(userId)) {
                userBaskets.remove(userId); // removes the entire map for this user
                save();
            }
        }
    }
    
}