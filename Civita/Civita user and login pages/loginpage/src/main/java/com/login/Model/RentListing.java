

package com.login.Model;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

// Implement Serializable if you plan to save/load objects directly to a file
public class RentListing implements Serializable {
    private static final long serialVersionUID = 1L; // For serialization

    private String flatType;
    private String rentPrice; // Consider using double/BigDecimal for currency
    private String flatNumber;
    private String compatibility;
    private String amenities;
    private List<String> imagePaths; // Stores the paths to the images

    public RentListing(String flatType, String rentPrice, String flatNumber, String compatibility, String amenities, List<String> imagePaths) {
        this.flatType = flatType;
        this.rentPrice = rentPrice;
        this.flatNumber = flatNumber;
        this.compatibility = compatibility;
        this.amenities = amenities;
        this.imagePaths = new ArrayList<>(imagePaths); // Defensive copy
    }

    // Getters for all properties
    public String getFlatType() {
        return flatType;
    }

    public String getRentPrice() {
        return rentPrice;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public String getCompatibility() {
        return compatibility;
    }

    public String getAmenities() {
        return amenities;
    }

    public List<String> getImagePaths() {
        return new ArrayList<>(imagePaths); // Return a copy
    }

    @Override
    public String toString() {
        return "RentListing{" +
               "flatType='" + flatType + '\'' +
               ", rentPrice='" + rentPrice + '\'' +
               ", flatNumber='" + flatNumber + '\'' +
               ", compatibility='" + compatibility + '\'' +
               ", amenities='" + amenities + '\'' +
               ", imagePaths=" + imagePaths +
               '}';
    }
}

