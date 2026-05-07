package com.login.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.login.Model.AdminProfile;
import com.login.services.FirebaseInitialize;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;

import com.google.firebase.cloud.FirestoreClient;

import javafx.application.Platform;

public final class AdminProfileController {

    private static final String COLLECTION_NAME = "admins"; // Assuming admins are also in the 'users' collection
    private static boolean isFirebaseInitialized = false;

     public AdminProfileController() { }

    
   Firestore db= FirebaseInitialize.getDB();
    public CompletableFuture<AdminProfile> fetchAdminProfile(String uid) {
        CompletableFuture<AdminProfile> future = new CompletableFuture<>();

        System.out.println("Calling fetchAdminProfile...");
        System.err.println("Error fetching admin profile for UID: " + uid);

        if (!isFirebaseInitialized) {
            future.complete(null);
            return future;
        }

        new Thread(() -> {
            try {
                DocumentSnapshot documentSnapshot = FirestoreClient.getFirestore()
                        .collection(COLLECTION_NAME)
                        .document(uid)
                        .get()
                        .get(); // blocking call

                if (documentSnapshot.exists()) {
                    String fullName = documentSnapshot.getString("fullName");
                    String email = documentSnapshot.getString("email");
                    AdminProfile profile = new AdminProfile(fullName, email);

                    Platform.runLater(() -> future.complete(profile));
                } else {
                    System.out.println("No such document for admin!");
                    Platform.runLater(() -> future.complete(null));
                }

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> future.completeExceptionally(e));
            }
        }).start();

        return future;
    }

    public void updateAdminProfile(String uid, String fullName, String dob, String emailId, String phoneNo, String userRole,
                                       String societyRegNo, String flatsOwned, String societyRole) {
        if (!isFirebaseInitialized) {
            System.err.println("Firebase not initialized. Cannot update admin profile.");
            return;
        }

        Map<String, Object> updates = new HashMap<>();
        if (fullName != null) updates.put("fullName", fullName);
        if (dob != null) updates.put("dob", dob);
        if (emailId != null) updates.put("email", emailId);
        if (phoneNo != null) updates.put("phoneNo", phoneNo);
        if (userRole != null) updates.put("role", userRole);
        if (societyRegNo != null) updates.put("societyRegNo", societyRegNo);
        if (flatsOwned != null) updates.put("flatsOwned", flatsOwned);
        if (societyRole != null) updates.put("societyRole", societyRole);

        db.collection(COLLECTION_NAME)
                .document(uid)
                .update(updates)
                .addListener(() -> System.out.println("Admin profile updated successfully in Firestore."), Platform::runLater);
    }
}
