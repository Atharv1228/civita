package com.login.Controller;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.login.Model.UserProfile;

import com.google.cloud.firestore.DocumentSnapshot;

import com.google.firebase.cloud.FirestoreClient;

import javafx.application.Platform;

public final class UserController {

    private static final String COLLECTION_NAME = "users"; // Your Firestore collection name
    private static boolean isFirebaseInitialized = false;

    public UserController() {
        initializeFirebase();

    }

    public void initializeFirebase() {
        // if (!isFirebaseInitialized) {
        //     try {
        //         FileInputStream serviceAccount =
        //                 new FileInputStream("src\\main\\resources\\civita-alpha-firebase-adminsdk-fbsvc-0ca705c544.json"); // Adjust path

        //         FirebaseOptions options = new FirebaseOptions.Builder()
        //                 .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        //                 .build();

        //         FirebaseApp.initializeApp(options);
        //         isFirebaseInitialized = true;
        //         System.out.println("Firebase initialized successfully!");

        //     } catch (IOException e) {
        //         e.printStackTrace();
        //         System.err.println("Error initializing Firebase!");
        //     }
        // }
    }

    // Modified to accept UID dynamically
    public CompletableFuture<UserProfile> fetchUserProfile(String uid) {
        CompletableFuture<UserProfile> future = new CompletableFuture<>();

        System.out.println("Calling fetchUserProfile...");
        System.err.println("Error fetching user profile for UID: " + uid);


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
                    String flatNo = documentSnapshot.getString("flatNo");
                    UserProfile profile = new UserProfile(fullName, email, flatNo);

                    Platform.runLater(() -> future.complete(profile));
                } else {
                    System.out.println("No such document!");
                    Platform.runLater(() -> future.complete(null));
                }

            } catch (Exception e) {

                e.printStackTrace();
                Platform.runLater(() -> future.completeExceptionally(e));
            }
        }).start();

        return future;
    }

    public void updateUserProfile(String uid, String fullName, String dob, String email, String phone, String role,
                                    String familyType, String numMembers, String parkingSlot) {
        if (!isFirebaseInitialized) {
            System.err.println("Firebase not initialized. Cannot update profile.");
            return;
        }

        Map<String, Object> updates = new HashMap<>();
        if (fullName != null) updates.put("fullName", fullName);
        if (dob != null) updates.put("dob", dob);
        if (email != null) updates.put("email", email);
        if (phone != null) updates.put("phone", phone);
        if (role != null) updates.put("role", role);
        if (familyType != null) updates.put("familyType", familyType);
        if (numMembers != null) updates.put("numMembers", numMembers);
        if (parkingSlot != null) updates.put("flatNo", parkingSlot); // Assuming parkingSlot maps to flatNo for update

        FirestoreClient.getFirestore().collection(COLLECTION_NAME)
                .document(uid)
                .update(updates)
                .addListener(() -> System.out.println("User profile updated successfully in Firestore."), Platform::runLater);
    }
}