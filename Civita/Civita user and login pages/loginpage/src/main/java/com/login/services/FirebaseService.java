package com.login.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.login.Model.Flat;

public class FirebaseService {

    // Method to add a Flat to Firestore
    public static void addFlat(Flat flat) {
        try {
            Firestore db = FirebaseInitialize.getDB(); // Ensure initialization before use
            ApiFuture<?> future = db.collection("flats").document().set(flat);
            future.get(); // Wait for completion
            System.out.println("Flat added to Firestore!");
        } catch (Exception e) {
            System.err.println("Error adding flat to Firestore: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to get Firestore instance
    public static Firestore getFirestoreInstance() {
        return FirebaseInitialize.getDB();
    }
}
