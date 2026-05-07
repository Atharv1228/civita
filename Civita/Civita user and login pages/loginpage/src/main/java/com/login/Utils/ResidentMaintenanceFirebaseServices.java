package com.login.Utils;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.login.Model.ResidentMaintenanceModel;
import com.login.services.FirebaseInitialize;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ResidentMaintenanceFirebaseServices {
    
    public static List<ResidentMaintenanceModel> fetchUserMaintenanceData(String userUid) {
        List<ResidentMaintenanceModel> maintenanceList = new ArrayList<>();
        Firestore db = FirebaseInitialize.getDB();
        try {
            // Get the specific user's document
            DocumentReference docRef = db.collection("users").document(userUid);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            
            if (document.exists()) {
                String amount = document.getString("amount");
                String date = document.getString("date");
                String status = document.getString("status");
                String flatNo = document.getString("flatNo");
                
                // FIXED: Default to "unpaid" if status is null
                if (status == null || status.isEmpty()) {
                    status = "Unpaid";
                    System.out.println("Status was null for user " + userUid + ", defaulting to 'unpaid'");
                }
                
                // Create a new ResidentMaintenanceModel
                ResidentMaintenanceModel maintenance = new ResidentMaintenanceModel(userUid, amount, date, status, flatNo);
                maintenanceList.add(maintenance);
                
                System.out.println("Fetched maintenance data for user " + userUid + ": " + maintenance.getAmount() + ", " + maintenance.getDate() + ", " + maintenance.getStatus());
            } else {
                System.out.println("No user document found for UID: " + userUid);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error fetching resident maintenance data for user " + userUid + ": " + e.getMessage());
            e.printStackTrace();
        }
        return maintenanceList;
    }

    public static boolean updateMaintenanceStatus(String userUid, String maintenanceId, String newStatus) {
        Firestore db = FirestoreClient.getFirestore();
        try {
            // Directly reference the user's document
            DocumentReference docRef = db.collection("users").document(userUid);
            Map<String, Object> updates = new HashMap<>();
            updates.put("status", newStatus);
            
            ApiFuture<WriteResult> writeResult = docRef.update(updates);
            writeResult.get(); // Wait for completion
            
            System.out.println("Maintenance status updated for user " + userUid + " to " + newStatus);
            return true;
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error updating maintenance status for user " + userUid + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addMaintenanceRecord(String userUid, String flatNo, String amount, String date, String status) {
        Firestore db = FirestoreClient.getFirestore();
        try {
            DocumentReference docRef = db.collection("users").document(userUid);
            Map<String, Object> data = new HashMap<>();
            data.put("flatNo", flatNo);
            data.put("amount", amount);
            data.put("date", date);
            data.put("status", status);
            
            ApiFuture<WriteResult> future = docRef.set(data, SetOptions.merge());
            future.get();
            
            System.out.println("Maintenance fields added/updated for user " + userUid);
            return true;
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error adding/updating maintenance record for user " + userUid + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static String getUidByFlatNo(String flatNo) {
        Firestore db = FirebaseInitialize.getDB();
        try {
            ApiFuture<QuerySnapshot> future = db.collection("users")
                                                 .whereEqualTo("flatNo", flatNo)
                                                 .get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            if (!documents.isEmpty()) {
                return documents.get(0).getId();
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error finding user by flatNo " + flatNo + ": " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}