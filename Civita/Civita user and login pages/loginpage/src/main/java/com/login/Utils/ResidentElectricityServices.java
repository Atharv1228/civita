package com.login.Utils;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;
import com.login.Model.ResidentElectricityModel;
import com.login.services.FirebaseInitialize;

import java.util.concurrent.CompletableFuture;

public class ResidentElectricityServices {
    
    private static void ensureFirebaseInitialized() {
        // Use centralized Firebase initialization
        if (FirebaseApp.getApps().isEmpty()) {
            System.out.println("Firebase not initialized, initializing via FirebaseInitialize...");
            FirebaseInitialize.initialize();
        }
        
        if (FirebaseApp.getApps().isEmpty()) {
            System.err.println("WARNING: Firebase still not initialized after attempt");
        } else {
            System.out.println("Firebase is ready");
        }
    }
    
    public static CompletableFuture<ResidentElectricityModel> fetchElectricityBillData(String flatNo) {
        CompletableFuture<ResidentElectricityModel> future = new CompletableFuture<>();
        
        try {
            ensureFirebaseInitialized();
            
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference electricityBillsRef = databaseRef.child("electricityBills").child(flatNo);
            
            System.out.println("=== FETCHING ELECTRICITY BILL DATA ===");
            System.out.println("Flat Number: " + flatNo);
            System.out.println("Firebase Path: electricityBills/" + flatNo);
            
            electricityBillsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        ResidentElectricityModel billData = new ResidentElectricityModel();
                        billData.setFlatNo(flatNo);
                        
                        System.out.println("=== FIREBASE RESPONSE ===");
                        System.out.println("DataSnapshot exists: " + dataSnapshot.exists());
                        System.out.println("DataSnapshot has children: " + dataSnapshot.hasChildren());
                        System.out.println("Children count: " + dataSnapshot.getChildrenCount());
                        
                        if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                            System.out.println("=== PROCESSING CHILDREN ===");
                            
                            String latestBillId = null;
                            DataSnapshot latestBillSnapshot = null;
                            
                            for (DataSnapshot billSnapshot : dataSnapshot.getChildren()) {
                                String billId = billSnapshot.getKey();
                                System.out.println("Found bill ID: " + billId);
                                
                                if (latestBillId == null || billId.compareTo(latestBillId) > 0) {
                                    latestBillId = billId;
                                    latestBillSnapshot = billSnapshot;
                                }
                            }
                            
                            if (latestBillSnapshot != null) {
                                System.out.println("=== PROCESSING LATEST BILL ===");
                                System.out.println("Latest bill ID: " + latestBillId);
                                
                                for (DataSnapshot child : latestBillSnapshot.getChildren()) {
                                    System.out.println("  " + child.getKey() + ": " + child.getValue());
                                }
                                
                                Object amountObj = latestBillSnapshot.child("amount").getValue();
                                Object dateObj = latestBillSnapshot.child("date").getValue();
                                Object imagePathObj = latestBillSnapshot.child("imagePath").getValue();
                                
                                Double amount = null;
                                if (amountObj != null) {
                                    try {
                                        if (amountObj instanceof Number) {
                                            amount = ((Number) amountObj).doubleValue();
                                        } else if (amountObj instanceof String) {
                                            amount = Double.parseDouble((String) amountObj);
                                        }
                                    } catch (Exception e) {
                                        System.err.println("Error processing amount: " + e.getMessage());
                                    }
                                }
                                
                                String date = dateObj != null ? dateObj.toString() : null;
                                String imagePath = imagePathObj != null ? imagePathObj.toString() : null;
                                
                                billData.setElectricityBillAmount(amount);
                                billData.setElectricityBillDate(date);
                                billData.setElectricityBillImagePath(imagePath);
                                
                                System.out.println("=== FINAL BILL DATA ===");
                                System.out.println("Final amount: " + billData.getElectricityBillAmount());
                                System.out.println("Final date: " + billData.getElectricityBillDate());
                                
                            } else {
                                setDefaultValues(billData);
                            }
                        } else {
                            System.out.println("No electricity bill data exists for flat: " + flatNo);
                            setDefaultValues(billData);
                        }
                        
                        future.complete(billData);
                        
                    } catch (Exception e) {
                        System.err.println("Error in data processing: " + e.getMessage());
                        e.printStackTrace();
                        
                        ResidentElectricityModel errorBillData = new ResidentElectricityModel();
                        errorBillData.setFlatNo(flatNo);
                        setDefaultValues(errorBillData);
                        future.complete(errorBillData);
                    }
                }
                
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.err.println("Firebase error: " + databaseError.getMessage());
                    
                    ResidentElectricityModel errorBillData = new ResidentElectricityModel();
                    errorBillData.setFlatNo(flatNo);
                    setDefaultValues(errorBillData);
                    future.complete(errorBillData);
                }
            });
            
        } catch (Exception e) {
            System.err.println("Exception in fetch method: " + e.getMessage());
            e.printStackTrace();
            
            ResidentElectricityModel errorBillData = new ResidentElectricityModel();
            errorBillData.setFlatNo(flatNo);
            setDefaultValues(errorBillData);
            future.complete(errorBillData);
        }
        
        return future;
    }
    
    private static void setDefaultValues(ResidentElectricityModel billData) {
        billData.setElectricityBillAmount(0.0);
        billData.setElectricityBillDate("No due date");
        billData.setElectricityBillImagePath(null);
        System.out.println("Set default values for bill data");
    }
    
    public static void updatePaymentStatus(String flatNo, boolean isPaid) {
        try {
            ensureFirebaseInitialized();
            
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference paymentStatusRef = databaseRef.child("electricityPayments").child(flatNo);
            
            paymentStatusRef.child("isPaid").setValue(isPaid, (databaseError, databaseReference) -> {
                if (databaseError != null) {
                    System.err.println("Failed to update payment status: " + databaseError.getMessage());
                } else {
                    System.out.println("Payment status updated successfully for Flat No: " + flatNo);
                }
            });
        } catch (Exception e) {
            System.err.println("Exception in updatePaymentStatus: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
