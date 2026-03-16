package com.ufund.api.ufundapi.persistence;

import com.fasterxml.jackson.core.type.TypeReference; 
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

@Component
public class BasketFileDAO implements BasketDAO{
    private static final Logger LOG = Logger.getLogger(BasketFileDAO.class.getName());
    private Map<Integer, Map<Integer, Need>> userBaskets;
    private final ObjectMapper objectMapper;
    private final String filename;

    public BasketFileDAO(@Value("${basket.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }



    private boolean save() throws IOException {
        objectMapper.writeValue(new File(filename), userBaskets);
        return true;
    }

    private boolean load() throws IOException {
        userBaskets = new TreeMap<>();

        File file = new File(filename);
        if (!file.exists() || file.length() == 0) {
            LOG.info("Basket file is missing or empty. Starting with empty basket.");
            return true;
        }

        userBaskets=objectMapper.readValue(file, new TypeReference<TreeMap<Integer, Map<Integer, Need>>>() {});

        return true;
    }
    @Override
    public Need[] getNeeds(int userId) throws IOException {
        synchronized (userBaskets) {
            Map<Integer, Need> myBasket = userBaskets.get(userId);
            if (myBasket == null) {
                return new Need[0];
            }
            return myBasket.values().toArray(new Need[0]);
        }
    }

    @Override
    public Need addToBasket(int userId, Need need) throws IOException {
        synchronized (userBaskets) {
            // Get user's basket or create a new TreeMap for them if it's their first item
            Map<Integer, Need> myBasket = userBaskets.computeIfAbsent(userId, k -> new TreeMap<>());

            // Logic: If the item already exists in the basket, update the quantity
            if (myBasket.containsKey(need.getId())) {
                Need existing = myBasket.get(need.getId());
                existing.setQuantity(existing.getQuantity() + need.getQuantity());
            } else {
                // Otherwise, add the new need to their basket
                myBasket.put(need.getId(), need);
            }

            save();
            return myBasket.get(need.getId());
        }
    }
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
    
}