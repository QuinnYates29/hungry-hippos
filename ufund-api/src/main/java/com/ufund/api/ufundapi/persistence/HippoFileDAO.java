package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Hippo;

/**
 * Implementation of HippoDAO using a JSON file for persistence.
 * * @author Quinn Yates (qry3977)
 * @author Ilia Zhdanov (iz6341)
 */
@Component
public class HippoFileDAO implements HippoDAO {
    private static final Logger LOG = Logger.getLogger(HippoFileDAO.class.getName());

    private Map<Integer, Hippo> hippos; 
    private ObjectMapper objectMapper;
    private String filename;
    private static int nextId;

    public HippoFileDAO(@Value("${hippos.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    private Hippo[] getHipposArray() {
        return hippos.values().toArray(new Hippo[0]);
    }

    private boolean save() throws IOException {
        Hippo[] hippoArray = getHipposArray();
        objectMapper.writeValue(new File(filename), hippoArray);
        return true;
    }

    private boolean load() throws IOException {
        hippos = new TreeMap<>();
        nextId = 0;

        File file = new File(filename);
        if (!file.exists() || file.length() == 0) {
            LOG.info("Hippo file missing or empty. Starting fresh.");
            return true;
        }

        Hippo[] hippoArray = objectMapper.readValue(file, Hippo[].class);
        for (Hippo hippo : hippoArray) {
            hippos.put(hippo.getId(), hippo);
            if (hippo.getId() > nextId) nextId = hippo.getId();
        }
        ++nextId;
        return true;
    }

    @Override
    public Hippo[] getHippos() {
        synchronized(hippos) {
            return getHipposArray();
        }
    }

    @Override
    public Hippo getHippo(int id) {
        synchronized(hippos) {
            return hippos.get(id);
        }
    }

    @Override
    public Hippo createHippo(Hippo hippo) throws IOException {
        synchronized(hippos) {
            Hippo newHippo = new Hippo(
                nextId(), 
                hippo.getName(), 
                hippo.getSpecies(), 
                hippo.getGender(), 
                hippo.getBirthdate(), 
                hippo.getWeight(), 
                hippo.getLatitude(), 
                hippo.getLongitude()
            );
            hippos.put(newHippo.getId(), newHippo);
            save();
            return newHippo;
        }
    }

    @Override
    public Hippo updateHippo(Hippo hippo) throws IOException {
        synchronized(hippos) {
            if (!hippos.containsKey(hippo.getId())) return null;
            hippos.put(hippo.getId(), hippo);
            save();
            return hippo;
        }
    }

    @Override
    public boolean deleteHippo(int id) throws IOException {
        synchronized(hippos) {
            if (hippos.containsKey(id)) {
                hippos.remove(id);
                save();
                return true;
            }
            return false;
        }
    }

    @Override
    public Hippo[] findHippos(String containsText) {
        synchronized(hippos) {
            ArrayList<Hippo> matches = new ArrayList<>();
            for (Hippo hippo : hippos.values()) {
                if (hippo.getName().toLowerCase().contains(containsText.toLowerCase())) {
                    matches.add(hippo);
                }
            }
            return matches.toArray(new Hippo[0]);
        }
    }
}