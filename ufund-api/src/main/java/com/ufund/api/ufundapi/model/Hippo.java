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

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSpecies() { return species; }
    public String getGender() { return gender; }
    public LocalDate getBirthdate() { return birthdate; }
    public double getWeight() { return weight; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSpecies(String species) { this.species = species; }
    public void setGender(String gender) { this.gender = gender; }
    public void setBirthdate(LocalDate birthdate) { this.birthdate = birthdate; }
    public void setWeight(double weight) { this.weight = weight; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    /**
     * Returns a readable string representation of the hippo.
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, name, species, latitude, longitude);
    }
}