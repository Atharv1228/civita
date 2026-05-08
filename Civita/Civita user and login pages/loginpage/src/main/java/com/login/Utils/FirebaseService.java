package com.login.Utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

public class FirebaseService {

    private static boolean initialized = false;
    private static final String DATABASE_URL = "https://civita-alpha-default-rtdb.firebaseio.com/";
    private static final String SERVICE_ACCOUNT_FILE = "civita-alpha-firebase-adminsdk-fbsvc-0ca705c544.json";

    public static void initialize() throws IOException {
        if (initialized || !FirebaseApp.getApps().isEmpty()) {
            initialized = true;
            return;
        }

        InputStream serviceAccount = null;
        
        // Try loading from classpath first (recommended for production)
        serviceAccount = FirebaseService.class.getClassLoader().getResourceAsStream(SERVICE_ACCOUNT_FILE);
        
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
            throw new IOException("Firebase service account file not found: " + SERVICE_ACCOUNT_FILE + 
                                  ". Please place it in src/main/resources/");
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(DATABASE_URL) 
                .build();

        FirebaseApp.initializeApp(options);
        initialized = true;
        System.out.println("Firebase initialized successfully via FirebaseService.");
    }

    public static Firestore getFirestore() throws IOException {
        initialize();
        return FirestoreClient.getFirestore();
    }

    public static FirebaseAuth getAuth() throws IOException {
        initialize();
        return FirebaseAuth.getInstance();
    }
    
    public static boolean isInitialized() {
        return initialized;
    }
}
