package com.login.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseInitializer {

    private static boolean initialized = false;

    static {
        initialize();
    }

    public static void initialize() {
        if (!initialized) {
            try {
                // Check if Firebase is already initialized
                if (FirebaseApp.getApps().isEmpty()) {
                    // Adjust the path to your service account key file
                    // It's recommended to place this in src/main/resources
                    FileInputStream serviceAccount = new FileInputStream("src\\main\\resources\\firebase-key.json");

                    FirebaseOptions options = FirebaseOptions.builder()
                            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                            .setDatabaseUrl("https://civita-alpha-default-rtdb.firebaseio.com/")
                            .build();

                    FirebaseApp.initializeApp(options);
                    initialized = true;
                    System.out.println("[FirebaseInitializer] ✅ Firebase initialized successfully.");
                } else {
                    initialized = true;
                    System.out.println("[FirebaseInitializer] Firebase already initialized.");
                }
            } catch (IOException e) {
                System.err.println("[FirebaseInitializer] ❌ Error initializing Firebase: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
