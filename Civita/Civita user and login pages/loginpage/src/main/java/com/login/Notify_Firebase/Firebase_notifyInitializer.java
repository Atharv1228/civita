package com.login.Notify_Firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

public class Firebase_notifyInitializer {
    private static boolean initialized = false;

    static {
        init();
    }

    public static void init() {
        if (!initialized) {
            try {
                System.out.println("[Firebase Notification] Initializing Firebase...");
                System.out.println("[Firebase Notification] Working Directory: " + System.getProperty("user.dir"));
                
                if (FirebaseApp.getApps().isEmpty()) {
                    // Use the correct path to the service account key
                    FileInputStream serviceAccount = new FileInputStream("src\\main\\resources\\firebase-key.json");

                    FirebaseOptions options = FirebaseOptions.builder()
                            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                            .build();

                    FirebaseApp.initializeApp(options);
                    initialized = true;
                    System.out.println("[Firebase Notification] ✅ Firebase initialized successfully");
                } else {
                    initialized = true;
                    System.out.println("[Firebase Notification] Firebase already initialized");
                }
            } catch (IOException e) {
                System.err.println("[Firebase Notification] ❌ Error initializing Firebase: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static Firestore getFirestore() {
        if (!initialized) {
            init();
        }
        return FirestoreClient.getFirestore();
    }
}
