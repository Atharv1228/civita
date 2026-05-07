package com.login.Model;

public class AdminMaintenanceModel {
    private String flatNo;
    private String amount;
    private String date;
    private String ownerName;

    public AdminMaintenanceModel() {} 

   
    public AdminMaintenanceModel(String flatNo, String amount, String date, String ownerName) {
        this.flatNo = flatNo;
        this.amount = amount;
        this.date = date;
        this.ownerName = ownerName; 
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}