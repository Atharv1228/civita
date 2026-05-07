package com.login.Model;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class SellListing implements Serializable {
    private static final long serialVersionUID = 1L;

    private String flatType;
    private String sellPrice; // Consider using double/BigDecimal for currency
    private String flatNumber;
    private String contactInfo; // Or more detailed contact model
    private List<String> imagePaths; // Stores the absolute paths to the images

    public SellListing(String flatType, String sellPrice, String flatNumber, String contactInfo, List<String> imagePaths) {
        this.flatType = flatType;
        this.sellPrice = sellPrice;
        this.flatNumber = flatNumber;
        this.contactInfo = contactInfo;
        this.imagePaths = new ArrayList<>(imagePaths); // Defensive copy
    }

    // Getters for all properties
    public String getFlatType() {
        return flatType;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public List<String> getImagePaths() {
        return new ArrayList<>(imagePaths); // Return a copy
    }

    @Override
    public String toString() {
        return "SellListing{" +
               "flatType='" + flatType + '\'' +
               ", sellPrice='" + sellPrice + '\'' +
               ", flatNumber='" + flatNumber + '\'' +
               ", contactInfo='" + contactInfo + '\'' +
               ", imagePaths=" + imagePaths +
               '}';
    }
}