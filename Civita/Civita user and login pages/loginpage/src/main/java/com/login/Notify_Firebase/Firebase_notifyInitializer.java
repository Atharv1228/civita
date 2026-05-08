package com.login.Notify_Firebase;

import com.google.firebase.FirebaseApp;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.login.services.FirebaseInitialize;

public class Firebase_notifyInitializer {
    
    public static void init() {
        // Use centralized Firebase initialization instead of separate initialization
        if (FirebaseApp.getApps().isEmpty()) {
            System.out.println("Firebase not initialized, initializing via FirebaseInitialize...");
            FirebaseInitialize.initialize();
        }
        
        if (!FirebaseApp.getApps().isEmpty()) {
            System.out.println("Firebase initialized successfully for notifications");
        } else {
            System.err.println("WARNING: Firebase could not be initialized");
        }
    }

    public static Firestore getFirestore() {
        // Ensure Firebase is initialized before getting Firestore
        if (FirebaseApp.getApps().isEmpty()) {
            init();
        }
        return FirestoreClient.getFirestore();
    }
}
