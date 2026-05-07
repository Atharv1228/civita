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
                // Adjust the path to your service account key file
                // It's recommended to place this in src/main/resources
                FileInputStream serviceAccount = new FileInputStream("src\\main\\resources\\civita-alpha-firebase-adminsdk-fbsvc-0ca705c544.json");

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://civita-alpha-default-rtdb.firebaseio.com/")
                        .build();

                FirebaseApp.initializeApp(options);
                initialized = true;
                System.out.println("Firebase initialized successfully.");
            } catch (IOException e) {
                System.err.println("Error initializing Firebase: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // public static void init() {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'init'");
    // }
}
