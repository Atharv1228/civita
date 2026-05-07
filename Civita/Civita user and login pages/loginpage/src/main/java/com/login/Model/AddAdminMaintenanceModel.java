package com.login.Model;

public class AddAdminMaintenanceModel {
    private String flatNo;
    private String amount;
    private String date;

    public AddAdminMaintenanceModel(String flatNo, String amount, String date , String ownerName) {
        this.flatNo = flatNo;
        this.amount = amount;
        this.date = date;
    }

    public AddAdminMaintenanceModel(String flatNo2, String amount2, String date2) {
        //TODO Auto-generated constructor stub
    }

    public String getFlatNo() {
        return flatNo;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    
    public String getOwnerName() {
        return getOwnerName();
    }
}
