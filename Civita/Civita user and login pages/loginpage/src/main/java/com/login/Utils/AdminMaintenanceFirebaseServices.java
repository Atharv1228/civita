package com.login.Utils;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.login.Model.AdminMaintenanceModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class AdminMaintenanceFirebaseServices {
    
    public static List<AdminMaintenanceModel> fetchFlatData() {
        List<AdminMaintenanceModel> flatList = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        try {
            ApiFuture<QuerySnapshot> future = db.collection("users").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot doc : documents) {
                String flatNo = doc.getString("flatNo");
                String fullName = doc.getString("fullName");
                String amount = doc.getString("amount");
                String date = doc.getString("date");
                if (flatNo != null && fullName != null) {
                    flatList.add(new AdminMaintenanceModel(flatNo, amount, date, fullName));
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error fetching flat data: " + e.getMessage());
            e.printStackTrace();
        }
        return flatList;
    }

    /**
     * FIXED: Updates the maintenance amount, date, and status for a specific flat in Firebase.
     * Now also sets the initial status to "unpaid" when maintenance data is added/updated.
     * @param flatNo The flat number to update.
     * @param amount The maintenance amount to set.
     * @param date The maintenance date to set.
     * @return true if the update was successful, false otherwise.
     */
    public static boolean updateFlatMaintenance(String flatNo, String amount, String date) {
        Firestore db = FirestoreClient.getFirestore();
        try {
            // Find the document that matches the flatNo
            ApiFuture<QuerySnapshot> future = db.collection("users").whereEqualTo("flatNo", flatNo).get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            
            if (documents.isEmpty()) {
                System.out.println("No document found for flatNo: " + flatNo);
                return false;
            }
            
            // Assuming flatNo is unique and there's only one matching document
            DocumentReference docRef = documents.get(0).getReference();
            Map<String, Object> updates = new HashMap<>();
            updates.put("amount", amount);
            updates.put("date", date);
            // FIXED: Set initial status to "unpaid" when maintenance is added/updated
            updates.put("status", "unpaid");
            
            ApiFuture<WriteResult> writeResult = docRef.update(updates);
            // Block until the update is complete
            writeResult.get();
            
            System.out.println("Maintenance updated for Flat No: " + flatNo + " with status: unpaid");
            return true;
            
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error updating flat maintenance for flatNo " + flatNo + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}