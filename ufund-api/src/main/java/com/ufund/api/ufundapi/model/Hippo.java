/**
 * Class representing the Hippo Data Object
 * Used for hippo tracking and identification
 * @author Quinn Yates (qry3977)
 * @author Ilia
 */
package com.ufund.api.ufundapi.model;

import java.time.LocalDate;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Hippo in the system.
 */
public class Hippo {
    private static final Logger LOG = Logger.getLogger(Hippo.class.getName());

    static final String STRING_FORMAT = "Hippo [id=%d, name=%s, species=%s, lat=%.4f, long=%.4f]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("species") private String species;
    @JsonProperty("gender") private String gender;
    @JsonProperty("birthDate") private LocalDate birthdate;
    @JsonProperty("weight") private double weight;
    @JsonProperty("latitude") private double latitude;
    @JsonProperty("longitude") private double longitude;

    /**
     * Creates a Hippo with the given information.
     */
    public Hippo(
        @JsonProperty("id") int id,
        @JsonProperty("name") String name,
        @JsonProperty("species") String species,
        @JsonProperty("gender") String gender,
        @JsonProperty("birthDate") LocalDate birthdate,
        @JsonProperty("weight") double weight,
        @JsonProperty("latitude") double latitude,
        @JsonProperty("longitude") double longitude
    ) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.gender = gender;
        this.birthdate = birthdate;
        this.weight = weight;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Get the id of the hippo.
     * @return The id of the hippo
     */
    public int getId() { return id; }

    /**
     * Get the name of the hippo.
     * @return The name of the hippo
     */
    public String getName() { return name; }

    /**
     * Get the species of the hippo.
     * @return The species of the hippo
     */
    public String getSpecies() { return species; }

    /**
     * Get the gender of the hippo.
     * @return The gender of the hippo
     */
    public String getGender() { return gender; }

    /**
     * Get the birthdate of the hippo.
     * @return The birthdate of the hippo
     */
    public LocalDate getBirthdate() { return birthdate; }

    /**
     * Get the weight of the hippo.
     * @return The weight of the hippo
     */
    public double getWeight() { return weight; }

    /**
     * Get the latitude of the hippo's location.
     * @return The latitude of the hippo's location
     */
    public double getLatitude() { return latitude; }

    /**
     * Get the longitude of the hippo's location.
     * @return The longitude of the hippo's location
     */
    public double getLongitude() { return longitude; }

    /**
     * Sets the id of the hippo - necessary for JSON object to Java object deserialization
     * @param id The id of the hippo
     */
    public void setId(int id) { this.id = id; }

    /**
    * Sets the name of the hippo - necessary for JSON object to Java object deserialization
    * @param name The name of the hippo
    */
    public void setName(String name) { this.name = name; }

    /**
    * Sets the species of the hippo - necessary for JSON object to Java object deserialization
    * @param species The species of the hippo
    */
    public void setSpecies(String species) { this.species = species; }

    /**
     * Sets the gender of the hippo - necessary for JSON object to Java object deserialization
     * @param gender The gender of the hippo
     */
    public void setGender(String gender) { this.gender = gender; }

    
    /**
     * Sets the birthdate of the hippo - necessary for JSON object to Java object deserialization
     * @param birthdate The birthdate of the hippo
     */
    public void setBirthdate(LocalDate birthdate) { this.birthdate = birthdate; }


    /**
     * Sets the weight of the hippo - necessary for JSON object to Java object deserialization
     * @param weight The weight of the hippo
     */
    public void setWeight(double weight) { this.weight = weight; }

    /**
     * Sets the latitude of the hippo's location - necessary for JSON object to Java object deserialization
     * @param latitude The latitude of the hippo's location
     */
    public void setLatitude(double latitude) { this.latitude = latitude; }


    /**
     * Sets the longitude of the hippo's location - necessary for JSON object to Java object deserialization
     * @param longitude The longitude of the hippo's location
     */
    public void setLongitude(double longitude) { this.longitude = longitude; }

    /**
     * Returns a readable string representation of the hippo.
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, name, species, latitude, longitude);
    }
}