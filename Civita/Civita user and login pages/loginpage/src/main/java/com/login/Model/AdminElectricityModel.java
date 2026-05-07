package com.login.Model;


public class AdminElectricityModel {
    private String flatNo;
    private String ownerName;
    // ... other existing fields

    private Double electricityBillAmount;
    private String electricityBillDate; // Or LocalDate if you prefer
    private String electricityBillImagePath; // Path to the local image

    // Constructors
    public AdminElectricityModel(String flatNo, String ownerName) {
        this.flatNo = flatNo;
        this.ownerName = ownerName;
    }

    // Constructor with electricity details (if you load them from Firebase)
    public AdminElectricityModel(String flatNo, String ownerName, Double electricityBillAmount, String electricityBillDate, String electricityBillImagePath) {
        this.flatNo = flatNo;
        this.ownerName = ownerName;
        this.electricityBillAmount = electricityBillAmount;
        this.electricityBillDate = electricityBillDate;
        this.electricityBillImagePath = electricityBillImagePath;
    }

    // Getters and Setters for all fields
    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Double getElectricityBillAmount() {
        return electricityBillAmount;
    }

    public void setElectricityBillAmount(Double electricityBillAmount) {
        this.electricityBillAmount = electricityBillAmount;
    }

    public String getElectricityBillDate() {
        return electricityBillDate;
    }

    public void setElectricityBillDate(String electricityBillDate) {
        this.electricityBillDate = electricityBillDate;
    }

    public String getElectricityBillImagePath() {
        return electricityBillImagePath;
    }

    public void setElectricityBillImagePath(String electricityBillImagePath) {
        this.electricityBillImagePath = electricityBillImagePath;
    }

    // For Firebase Firestore, you might also need a no-argument constructor
    // if you are using automatic object mapping.
    public AdminElectricityModel() {
    }
}