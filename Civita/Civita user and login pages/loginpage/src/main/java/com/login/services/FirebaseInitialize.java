package com.login.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import com.login.Model.Flat;

import com.google.cloud.firestore.Firestore;

import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseInitialize {

    static {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                FileInputStream serviceAccount = new FileInputStream("src\\main\\resources\\civita-alpha-firebase-adminsdk-fbsvc-0ca705c544.json");

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addFlat(Flat flat) {
        Firestore db = FirestoreClient.getFirestore();
        db.collection("flats").add(flat);
    }

    public static Firestore getDB() {
        return FirestoreClient.getFirestore();
    }
}