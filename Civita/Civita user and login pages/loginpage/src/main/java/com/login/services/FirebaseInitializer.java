package com.login.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

public class FirebaseInitializer {

    private static boolean initialized = false;
    private static boolean initializationAttempted = false;
    private static Exception initializationError = null;
    
    // Firebase configuration constants
    private static final String DATABASE_URL = "https://civita-alpha-default-rtdb.firebaseio.com/";
    private static final String SERVICE_ACCOUNT_FILE = "civita-alpha-firebase-adminsdk-fbsvc-0ca705c544.json";

    static {
        initialize();
    }

    public static void initialize() {
        if (initializationAttempted) {
            return;
        }
        initializationAttempted = true;
        
        if (FirebaseApp.getApps().isEmpty()) {
            try {
                InputStream serviceAccount = null;
                
                // Try loading from classpath first (recommended for production)
                serviceAccount = FirebaseInitializer.class.getClassLoader().getResourceAsStream(SERVICE_ACCOUNT_FILE);
                
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
                    throw new IOException("Firebase service account file not found. Please ensure '" + SERVICE_ACCOUNT_FILE + "' is in the resources folder.");
                }

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl(DATABASE_URL)
                        .build();

                FirebaseApp.initializeApp(options);
                initialized = true;
                System.out.println("Firebase initialized successfully.");
                
            } catch (IOException e) {
                initializationError = e;
                System.err.println("Error initializing Firebase: " + e.getMessage());
                System.err.println("Please ensure the Firebase service account JSON file is placed in src/main/resources/");
                e.printStackTrace();
            }
        } else {
            initialized = true;
        }
    }
    
    /**
     * Check if Firebase is properly initialized
     */
    public static boolean isInitialized() {
        return initialized;
    }
    
    /**
     * Get the initialization error if any
     */
    public static Exception getInitializationError() {
        return initializationError;
    }
}
