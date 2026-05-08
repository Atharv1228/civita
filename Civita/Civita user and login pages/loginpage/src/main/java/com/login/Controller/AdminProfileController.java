package com.login.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.login.Model.AdminProfile;
import com.login.services.FirebaseInitialize;
import com.login.Utils.UserSession;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;

import com.google.firebase.cloud.FirestoreClient;

import javafx.application.Platform;

public final class AdminProfileController {

    private static final String COLLECTION_NAME = "admins";

    public AdminProfileController() { }

    /**
     * Fetch admin profile using session UID or provided UID
     */
    public CompletableFuture<AdminProfile> fetchAdminProfile(String uid) {
        CompletableFuture<AdminProfile> future = new CompletableFuture<>();

        // If uid is null, try to get from session
        if (uid == null || uid.isEmpty()) {
            UserSession session = UserSession.getInstance();
            if (session.isLoggedIn()) {
                uid = session.getUid();
            }
        }
        
        final String finalUid = uid;
        System.out.println("Fetching admin profile for UID: " + finalUid);

        if (finalUid == null || finalUid.isEmpty()) {
            System.err.println("No UID available for fetching admin profile");
            future.complete(null);
            return future;
        }

        new Thread(() -> {
            try {
                Firestore db = FirebaseInitialize.getDB();
                if (db == null) {
                    System.err.println("Firebase not initialized");
                    Platform.runLater(() -> future.complete(null));
                    return;
                }
                
                DocumentSnapshot documentSnapshot = db.collection(COLLECTION_NAME)
                        .document(finalUid)
                        .get()
                        .get();

                if (documentSnapshot.exists()) {
                    String fullName = documentSnapshot.getString("fullName");
                    String email = documentSnapshot.getString("email");
                    AdminProfile profile = new AdminProfile(fullName, email);

                    System.out.println("Admin profile fetched successfully: " + fullName);
                    Platform.runLater(() -> future.complete(profile));
                } else {
                    // Try users collection as fallback
                    DocumentSnapshot userDoc = db.collection("users")
                            .document(finalUid)
                            .get()
                            .get();
                    
                    if (userDoc.exists()) {
                        String fullName = userDoc.getString("fullName");
                        String email = userDoc.getString("email");
                        AdminProfile profile = new AdminProfile(fullName, email);
                        Platform.runLater(() -> future.complete(profile));
                    } else {
                        System.out.println("No document found for admin UID: " + finalUid);
                        Platform.runLater(() -> future.complete(null));
                    }
                }

            } catch (Exception e) {
                System.err.println("Error fetching admin profile: " + e.getMessage());
                e.printStackTrace();
                Platform.runLater(() -> future.completeExceptionally(e));
            }
        }).start();

        return future;
    }

    /**
     * Update admin profile in Firestore
     */
    public void updateAdminProfile(String uid, String fullName, String dob, String emailId, String phoneNo, String userRole,
                                       String societyRegNo, String flatsOwned, String societyRole) {
        // If uid is null, try to get from session
        if (uid == null || uid.isEmpty()) {
            UserSession session = UserSession.getInstance();
            if (session.isLoggedIn()) {
                uid = session.getUid();
            }
        }
        
        if (uid == null || uid.isEmpty()) {
            System.err.println("Cannot update admin profile: No UID available");
            return;
        }

        Firestore db = FirebaseInitialize.getDB();
        if (db == null) {
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
        
        // Update session as well
        UserSession session = UserSession.getInstance();
        if (fullName != null) session.setFullName(fullName);
        if (emailId != null) session.setEmail(emailId);

        final String finalUid = uid;
        db.collection(COLLECTION_NAME)
                .document(finalUid)
                .update(updates)
                .addListener(() -> {
                    System.out.println("Admin profile updated successfully in Firestore for UID: " + finalUid);
                }, Platform::runLater);
    }
}
