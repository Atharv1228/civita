package com.login.Utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseService {

    private static boolean initialized = false;

    public static void initialize() throws IOException {
        if (initialized) return;

        FileInputStream serviceAccount = new FileInputStream("src\\main\\resources\\civita-alpha-firebase-adminsdk-fbsvc-0ca705c544.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://civita-alpha-default-rtdb.firebaseio.com/") 
                .build();

        FirebaseApp.initializeApp(options);
        initialized = true;
    }

    public static Firestore getFirestore() throws IOException {
        initialize();
        return FirestoreClient.getFirestore();
    }

    public static FirebaseAuth getAuth() throws IOException {
        initialize();
        return FirebaseAuth.getInstance();
    }
}
