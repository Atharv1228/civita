package com.login.Controller;

import com.google.api.core.ApiFuture;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.firestore.DocumentReference;
import com.login.Model.SignupModel;
import com.login.Utils.FirebaseService;

import java.util.HashMap;
import java.util.Map;

public class SignupController {

    public static String registerUser(SignupModel user) {
        try {
            if (!user.isPasswordMatching()) {
                return "Passwords do not match!";
            }

            // Create Firebase Auth account
            CreateRequest request = new CreateRequest()
                    .setEmail(user.getEmail())
                    .setPassword(user.getPassword());

            UserRecord userRecord = FirebaseService.getAuth().createUser(request);
            String uid = userRecord.getUid();

            // Store user info in Firestore
            Firestore db = FirebaseService.getFirestore();
            DocumentReference docRef = db.collection("users").document(uid);

            Map<String, Object> userData = new HashMap<>();
            userData.put("fullName", user.getFullName());
            userData.put("email", user.getEmail());
            userData.put("flatNo", user.getFlatNo());

            ApiFuture<WriteResult> result = docRef.set(userData);
            result.get();

            return " Signup successful!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
