package com.login.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import com.login.Model.Flat;

import com.google.cloud.firestore.Firestore;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

public class FirebaseInitialize {
    
    private static boolean initialized = false;
    private static final String DATABASE_URL = "https://civita-alpha-default-rtdb.firebaseio.com/";
    private static final String SERVICE_ACCOUNT_FILE = "civita-alpha-firebase-adminsdk-fbsvc-0ca705c544.json";

    static {
        initializeFirebase();
    }
    
    private static void initializeFirebase() {
        if (initialized || !FirebaseApp.getApps().isEmpty()) {
            initialized = true;
            return;
        }
        
        try {
            InputStream serviceAccount = null;
            
            // Try loading from classpath first (recommended for production)
            serviceAccount = FirebaseInitialize.class.getClassLoader().getResourceAsStream(SERVICE_ACCOUNT_FILE);
            
            // If not found in classpath, try file system paths
            if (serviceAccount == null) {
                String[] possiblePaths = {
                    "src/main/resources/" + SERVICE_ACCOUNT_FILE,
                    "src\\main\\resources\\" + SERVICE_ACCOUNT_FILE,
                    SERVICE_ACCOUNT_FILE
                };
                
                for (String path : possiblePaths) {
                    try {
                        java.io.File file = new java.io.File(path);
                        if (file.exists()) {
                            serviceAccount = new FileInputStream(file);
                            System.out.println("Firebase service account loaded from: " + path);
                            break;
                        }
                    } catch (Exception e) {
                        // Continue trying other paths
                    }
                }
            }
            
            if (serviceAccount == null) {
                System.err.println("Firebase service account file not found: " + SERVICE_ACCOUNT_FILE);
                System.err.println("Please place the file in src/main/resources/");
                return;
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(DATABASE_URL)
                    .build();

            FirebaseApp.initializeApp(options);
            initialized = true;
            System.out.println("Firebase initialized successfully via FirebaseInitialize.");
            
        } catch (IOException e) {
            System.err.println("Error initializing Firebase: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void addFlat(Flat flat) {
        if (!initialized) {
            System.err.println("Firebase not initialized. Cannot add flat.");
            return;
        }
        Firestore db = FirestoreClient.getFirestore();
        db.collection("flats").add(flat);
    }

    public static Firestore getDB() {
        if (!initialized) {
            initializeFirebase();
        }
        if (!initialized) {
            System.err.println("Firebase not initialized. Cannot get Firestore instance.");
            return null;
        }
        return FirestoreClient.getFirestore();
    }
    
    public static boolean isInitialized() {
        return initialized;
    }
}
