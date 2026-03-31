package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.Hippo;

/**
 * Defines the contract for Hippo data persistence.
 * Provides standard CRUD (Create, Read, Update, Delete) operations
 * for managing Hippo objects in the system.
 * * @author Quinn Yates (qry3977)
 * @author Ilia
 */
public interface HippoDAO {

    /**
     * Retrieves all {@link Hippo} objects currently in persistence.
     * * @return An array of all Hippos; may be empty but never null.
     * @throws IOException if there is an issue accessing the underlying storage.
     */
    Hippo[] getHippos() throws IOException;

    /**
     * Finds a single {@link Hippo} by its unique identifier.
     * * @param id The unique integer ID of the hippo to retrieve.
     * @return The Hippo object if found; null otherwise.
     * @throws IOException if there is an issue accessing the underlying storage.
     */
    Hippo getHippo(int id) throws IOException;

    /**
     * Persists a new {@link Hippo} object. 
     * The DAO is responsible for ensuring the ID is unique or handling ID generation.
     * * @param hippo The Hippo object to be created.
     * @return The newly created Hippo object (potentially with a newly assigned ID).
     * @throws IOException if there is an issue writing to the storage.
     */
    Hippo createHippo(Hippo hippo) throws IOException;

    /**
     * Updates an existing {@link Hippo} in the system.
     * * @param hippo The Hippo object containing the updated information.
     * @return The updated Hippo object if successful; null if the hippo does not exist.
     * @throws IOException if there is an issue updating the storage.
     */
    Hippo updateHippo(Hippo hippo) throws IOException;

    /**
     * Removes a {@link Hippo} from the system based on its ID.
     * * @param id The unique integer ID of the hippo to delete.
     * @return true if the hippo was successfully deleted; false if it was not found.
     * @throws IOException if there is an issue accessing the storage.
     */
    boolean deleteHippo(int id) throws IOException;

    /**
     * Searches for hippos whose names contain the given search string.
     * (Useful for search bars in your Hungry Hippos UI!)
     * * @param containsText The string to search for within hippo names.
     * @return An array of Hippos that match the search criteria.
     * @throws IOException if there is an issue accessing the storage.
     */
    Hippo[] findHippos(String containsText) throws IOException;
}