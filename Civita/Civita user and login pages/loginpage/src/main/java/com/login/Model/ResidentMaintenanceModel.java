package com.login.Model;

public class ResidentMaintenanceModel {
    private String id; // To store the Firestore document ID for updating status
    private String amount;
    private String date; // Due date
    private String status; // "unpaid" or "paid"
    private String flatNo; // To link back to the flat if needed, though for resident, it's implicit

    public ResidentMaintenanceModel() {
        // Default constructor required for Firebase deserialization
    }

    public ResidentMaintenanceModel(String id, String amount, String date, String status, String flatNo) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.status = status;
        this.flatNo = flatNo;
    }

    // --- Getters ---
    public String getId() {
        return id;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getFlatNo() {
        return flatNo;
    }

    // --- Setters ---
    public void setId(String id) {
        this.id = id;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }
}