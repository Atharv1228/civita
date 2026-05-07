package com.login.Model;

import java.util.List;

public class Flat {
    private String flatNo;
    private String flatType;
    private String amenities;
    private String compatibleFor;
    private String price;
    private List<String> imageUrls; // Needed for rentFlat

    // Constructor for sellFlat (without images)
    public Flat(String flatNo, String flatType, String amenities, String compatibleFor, String price) {
        this.flatNo = flatNo;
        this.flatType = flatType;
        this.amenities = amenities;
        this.compatibleFor = compatibleFor;
        this.price = price;
    }

    // Constructor for rentFlat (with images)
    public Flat(String flatNo, String flatType, String amenities, String compatibleFor, String price, List<String> imageUrls) {
        this.flatNo = flatNo;
        this.flatType = flatType;
        this.amenities = amenities;
        this.compatibleFor = compatibleFor;
        this.price = price;
        this.imageUrls = imageUrls;
    }

    // Getters
    public String getFlatNo() {
        return flatNo;
    }

    public String getFlatType() {
        return flatType;
    }

    public String getAmenities() {
        return amenities;
    }

    public String getCompatibleFor() {
        return compatibleFor;
    }

    public String getPrice() {
        return price;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    
}
