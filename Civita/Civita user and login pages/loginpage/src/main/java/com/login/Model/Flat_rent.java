package com.login.Model;

import java.util.List;

public class Flat_rent {
    private String flatNo, flatType, amenities, compatibleFor, price;
    private List<String> imageUrls;

    public Flat_rent() {}

    public Flat_rent(String flatNo, String flatType, String amenities, String compatibleFor, String price, List<String> imageUrls) {
        this.flatNo = flatNo;
        this.flatType = flatType;
        this.amenities = amenities;
        this.compatibleFor = compatibleFor;
        this.price = price;
        this.imageUrls = imageUrls;
    }

    public String getFlatNo() { return flatNo; }
    public String getFlatType() { return flatType; }
    public String getAmenities() { return amenities; }
    public String getCompatibleFor() { return compatibleFor; }
    public String getPrice() { return price; }
    public List<String> getImageUrls() { return imageUrls; }

    public void setFlatNo(String flatNo) { this.flatNo = flatNo; }
    public void setFlatType(String flatType) { this.flatType = flatType; }
    public void setAmenities(String amenities) { this.amenities = amenities; }
    public void setCompatibleFor(String compatibleFor) { this.compatibleFor = compatibleFor; }
    public void setPrice(String price) { this.price = price; }
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }
}
