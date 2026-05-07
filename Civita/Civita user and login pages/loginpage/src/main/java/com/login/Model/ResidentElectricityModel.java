package com.login.Model;

public class ResidentElectricityModel {
    private String flatNo;
    private Double electricityBillAmount;
    private String electricityBillDate;
    private String electricityBillImagePath;
    private boolean isPaid;
    
    // Default constructor
    public ResidentElectricityModel() {
    }
    
    // Constructor with parameters
    public ResidentElectricityModel(String flatNo, Double electricityBillAmount, 
                                  String electricityBillDate, String electricityBillImagePath) {
        this.flatNo = flatNo;
        this.electricityBillAmount = electricityBillAmount;
        this.electricityBillDate = electricityBillDate;
        this.electricityBillImagePath = electricityBillImagePath;
        this.isPaid = false; // Default to unpaid
    }
    
    // Getters and Setters
    public String getFlatNo() {
        return flatNo;
    }
    
    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
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
    
    public boolean isPaid() {
        return isPaid;
    }
    
    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}