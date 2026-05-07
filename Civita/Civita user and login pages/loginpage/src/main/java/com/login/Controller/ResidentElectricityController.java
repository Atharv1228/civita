package com.login.Controller;

import com.login.Model.ResidentElectricityModel;
import com.login.Utils.ResidentElectricityServices;
import com.google.firebase.database.*;
import java.util.concurrent.CompletableFuture;

public class ResidentElectricityController {
    
    public static CompletableFuture<ResidentElectricityModel> getElectricityBillData(String flatNo) {
        System.out.println("Controller: Getting electricity bill data for flat: " + flatNo);
        return ResidentElectricityServices.fetchElectricityBillData(flatNo);
    }
    
    // Debug method to check what's actually in Firebase
    public static void debugFirebaseData(String flatNo) {
        try {
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference electricityBillsRef = databaseRef.child("electricityBills");
            
            System.out.println("=== DEBUGGING FIREBASE DATA ===");
            System.out.println("Checking path: electricityBills");
            
            electricityBillsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    System.out.println(" ALL ELECTRICITY BILLS ");
                    System.out.println("Total flats with bills: " + dataSnapshot.getChildrenCount());
                    
                    for (DataSnapshot flatSnapshot : dataSnapshot.getChildren()) {
                        String currentFlatNo = flatSnapshot.getKey();
                        System.out.println("Flat: " + currentFlatNo + " (Bills: " + flatSnapshot.getChildrenCount() + ")");
                        
                        if (currentFlatNo.equals(flatNo)) {
                            System.out.println("=== FOUND TARGET FLAT: " + flatNo + " ===");
                            for (DataSnapshot billSnapshot : flatSnapshot.getChildren()) {
                                String billId = billSnapshot.getKey();
                                System.out.println("  Bill ID: " + billId);
                                System.out.println("    Amount: " + billSnapshot.child("amount").getValue());
                                System.out.println("    Date: " + billSnapshot.child("date").getValue());
                                System.out.println("    ImagePath: " + billSnapshot.child("imagePath").getValue());
                            }
                        }
                    }
                }
                
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.err.println("Debug cancelled: " + databaseError.getMessage());
                }
            });
            
        } catch (Exception e) {
            System.err.println("Debug exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void markBillAsPaid(String flatNo) {
        System.out.println("Controller: Marking bill as paid for flat: " + flatNo);
        ResidentElectricityServices.updatePaymentStatus(flatNo, true);
    }
    
    public static void markBillAsUnpaid(String flatNo) {
        System.out.println("Controller: Marking bill as unpaid for flat: " + flatNo);
        ResidentElectricityServices.updatePaymentStatus(flatNo, false);
    }
}