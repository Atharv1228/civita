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

    /**
     * Register a new user. If flatNo is empty or "ADMIN", user is registered as admin.
     * Otherwise, user is registered as a resident.
     */
    public static String registerUser(SignupModel user) {
        try {
            if (!user.isPasswordMatching()) {
                return "Passwords do not match!";
            }
            
            // Validate required fields
            if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
                return "Full name is required!";
            }
            
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                return "Email is required!";
            }
            
            if (user.getPassword() == null || user.getPassword().length() < 6) {
                return "Password must be at least 6 characters!";
            }

            // Create Firebase Auth account
            CreateRequest request = new CreateRequest()
                    .setEmail(user.getEmail())
                    .setPassword(user.getPassword());

            UserRecord userRecord = FirebaseService.getAuth().createUser(request);
            String uid = userRecord.getUid();

            // Determine if user is admin or resident based on flatNo
            String flatNo = user.getFlatNo();
            boolean isAdmin = (flatNo == null || flatNo.trim().isEmpty() || 
                              flatNo.equalsIgnoreCase("ADMIN") || 
                              flatNo.equalsIgnoreCase("admin"));
            
            String role = isAdmin ? "admin" : "resident";
            String collectionName = isAdmin ? "admins" : "users";

            // Store user info in appropriate Firestore collection
            Firestore db = FirebaseService.getFirestore();
            DocumentReference docRef = db.collection(collectionName).document(uid);

            Map<String, Object> userData = new HashMap<>();
            userData.put("fullName", user.getFullName());
            userData.put("email", user.getEmail());
            userData.put("role", role);
            userData.put("createdAt", System.currentTimeMillis());
            
            // Add flatNo only for residents
            if (!isAdmin) {
                userData.put("flatNo", flatNo);
            } else {
                userData.put("flatNo", "N/A"); // Admin doesn't have flat number
            }

            ApiFuture<WriteResult> result = docRef.set(userData);
            result.get(); // Wait for the write to complete

            String successMessage = isAdmin ? 
                "Admin account created successfully!" : 
                "Resident account created successfully!";
            
            System.out.println("User registered: " + user.getEmail() + " as " + role + " (UID: " + uid + ")");
            
            return successMessage;
            
        } catch (com.google.firebase.auth.FirebaseAuthException e) {
            System.err.println("Firebase Auth Error: " + e.getMessage());
            if (e.getMessage().contains("EMAIL_EXISTS") || e.getMessage().contains("already exists")) {
                return "This email is already registered. Please sign in instead.";
            }
            return "Registration failed: " + e.getMessage();
        } catch (Exception e) {
            System.err.println("Registration Error: " + e.getMessage());
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * Register a user with a specific role
     */
    public static String registerUserWithRole(SignupModel user, String role) {
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

            // Determine collection based on role
            String collectionName = "admin".equalsIgnoreCase(role) ? "admins" : "users";

            // Store user info in Firestore
            Firestore db = FirebaseService.getFirestore();
            DocumentReference docRef = db.collection(collectionName).document(uid);

            Map<String, Object> userData = new HashMap<>();
            userData.put("fullName", user.getFullName());
            userData.put("email", user.getEmail());
            userData.put("flatNo", user.getFlatNo() != null ? user.getFlatNo() : "N/A");
            userData.put("role", role.toLowerCase());
            userData.put("createdAt", System.currentTimeMillis());

            ApiFuture<WriteResult> result = docRef.set(userData);
            result.get();

            return "Registration successful!";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
