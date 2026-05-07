package com.login.Utils;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;
import com.login.Model.ResidentElectricityModel;
import java.util.concurrent.CompletableFuture;
import java.io.FileInputStream;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseOptions;

public class ResidentElectricityServices {
    
    private static void ensureFirebaseInitialized() {
        try {
            FirebaseApp.getInstance();
            System.out.println("Firebase is already initialized");
        } catch (IllegalStateException e) {
            try {
                System.out.println("Initializing Firebase...");
                FileInputStream serviceAccount = new FileInputStream("src\\main\\resources\\civita-alpha-firebase-adminsdk-fbsvc-0ca705c544.json");
                FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://civita-alpha-default-rtdb.firebaseio.com/")
                    .build();
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase initialized successfully");
            } catch (Exception initException) {
                System.err.println("Failed to initialize Firebase: " + initException.getMessage());
                initException.printStackTrace();
            }
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
            
            // Fetch all data for this flat without any ordering or limiting
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
                            
                            // Find the latest bill entry by iterating through all children
                            String latestBillId = null;
                            DataSnapshot latestBillSnapshot = null;
                            
                            for (DataSnapshot billSnapshot : dataSnapshot.getChildren()) {
                                String billId = billSnapshot.getKey();
                                System.out.println("Found bill ID: " + billId);
                                
                                // Firebase push IDs are chronologically ordered, so we can compare them
                                if (latestBillId == null || billId.compareTo(latestBillId) > 0) {
                                    latestBillId = billId;
                                    latestBillSnapshot = billSnapshot;
                                }
                            }
                            
                            if (latestBillSnapshot != null) {
                                System.out.println("=== PROCESSING LATEST BILL ===");
                                System.out.println("Latest bill ID: " + latestBillId);
                                
                                // Print all children of the latest bill
                                System.out.println("Bill data structure:");
                                for (DataSnapshot child : latestBillSnapshot.getChildren()) {
                                    System.out.println("  " + child.getKey() + ": " + child.getValue() + " (Type: " + 
                                        (child.getValue() != null ? child.getValue().getClass().getSimpleName() : "null") + ")");
                                }
                                
                                // Extract data with multiple approaches
                                Object amountObj = latestBillSnapshot.child("amount").getValue();
                                Object dateObj = latestBillSnapshot.child("date").getValue();
                                Object imagePathObj = latestBillSnapshot.child("imagePath").getValue();
                                
                                System.out.println("=== RAW DATA EXTRACTION ===");
                                System.out.println("Amount object: " + amountObj);
                                System.out.println("Date object: " + dateObj);
                                System.out.println("ImagePath object: " + imagePathObj);
                                
                                // Process amount
                                Double amount = null;
                                if (amountObj != null) {
                                    try {
                                        if (amountObj instanceof Number) {
                                            amount = ((Number) amountObj).doubleValue();
                                        } else if (amountObj instanceof String) {
                                            amount = Double.parseDouble((String) amountObj);
                                        }
                                        System.out.println("Processed amount: " + amount);
                                    } catch (Exception e) {
                                        System.err.println("Error processing amount: " + e.getMessage());
                                    }
                                }
                                
                                // Process date
                                String date = null;
                                if (dateObj != null) {
                                    date = dateObj.toString();
                                    System.out.println("Processed date: " + date);
                                }
                                
                                // Process image path
                                String imagePath = null;
                                if (imagePathObj != null) {
                                    imagePath = imagePathObj.toString();
                                    System.out.println("Processed imagePath: " + imagePath);
                                }
                                
                                // Set the data
                                billData.setElectricityBillAmount(amount);
                                billData.setElectricityBillDate(date);
                                billData.setElectricityBillImagePath(imagePath);
                                
                                System.out.println("=== FINAL BILL DATA ===");
                                System.out.println("Final amount: " + billData.getElectricityBillAmount());
                                System.out.println("Final date: " + billData.getElectricityBillDate());
                                System.out.println("Final imagePath: " + billData.getElectricityBillImagePath());
                                
                            } else {
                                System.out.println("No valid bill snapshot found");
                                setDefaultValues(billData);
                            }
                        } else {
                            System.out.println("=== NO DATA FOUND ===");
                            System.out.println("No electricity bill data exists for flat: " + flatNo);
                            setDefaultValues(billData);
                        }
                        
                        future.complete(billData);
                        
                    } catch (Exception e) {
                        System.err.println("=== ERROR IN DATA PROCESSING ===");
                        System.err.println("Error: " + e.getMessage());
                        e.printStackTrace();
                        
                        ResidentElectricityModel errorBillData = new ResidentElectricityModel();
                        errorBillData.setFlatNo(flatNo);
                        setDefaultValues(errorBillData);
                        future.complete(errorBillData);
                    }
                }
                
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.err.println("=== FIREBASE ERROR ===");
                    System.err.println("Error message: " + databaseError.getMessage());
                    System.err.println("Error code: " + databaseError.getCode());
                    System.err.println("Error details: " + databaseError.getDetails());
                    
                    ResidentElectricityModel errorBillData = new ResidentElectricityModel();
                    errorBillData.setFlatNo(flatNo);
                    setDefaultValues(errorBillData);
                    future.complete(errorBillData);
                }
            });
            
        } catch (Exception e) {
            System.err.println("=== EXCEPTION IN FETCH METHOD ===");
            System.err.println("Exception: " + e.getMessage());
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
