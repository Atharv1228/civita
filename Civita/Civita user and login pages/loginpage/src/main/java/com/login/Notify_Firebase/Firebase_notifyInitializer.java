package com.login.Notify_Firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

public class Firebase_notifyInitializer {
    public static void init() {
        try {
            // 🔍 Add this line to debug file path
            System.out.println("Working Dir: " + System.getProperty("user.dir"));
            FileInputStream serviceAccount = new FileInputStream("demo/src/main/resources/firebase-key.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
            System.out.println("✅ Firebase initialized successfully");
        } catch (IOException e) {
            e.printStackTrace(); // 🔥 Always print for debugging
        }
    }

    public static Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }
}