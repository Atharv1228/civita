package com.login.Utils;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.json.JSONObject;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

public class SigninFirebaseServices {
    private static final String FIREBASE_API_KEY = "AIzaSyD25nvpaX39IaU1d7hTw2qr9sm26W0tr3U";

    /**
     * Authenticate user with Firebase Authentication REST API
     */
    public static JSONObject authenticateUser(String email, String password) {
        String url = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + FIREBASE_API_KEY;

        JSONObject body = new JSONObject();
        body.put("email", email);
        body.put("password", password);
        body.put("returnSecureToken", true);

        try {
            HttpResponse<JsonNode> response = Unirest.post(url)
                    .header("Content-Type", "application/json")
                    .body(body.toString())
                    .asJson();

            if (response.getStatus() == 200) {
                return new JSONObject()
                    .put("status", "success")
                    .put("data", response.getBody().getObject());
            } else {
                JSONObject errorBody = response.getBody().getObject();
                String errorMessage = "Login failed";
                
                if (errorBody.has("error")) {
                    JSONObject error = errorBody.getJSONObject("error");
                    errorMessage = error.optString("message", "Login failed");
                }
                
                return new JSONObject()
                    .put("status", "error")
                    .put("message", errorMessage);
            }
        } catch (Exception e) {
            System.err.println("Authentication error: " + e.getMessage());
            return new JSONObject()
                .put("status", "error")
                .put("message", "Connection error. Please check your internet connection.");
        }
    }
    
    /**
     * Initialize user session by fetching user data from Firestore
     * Checks both 'admins' and 'users' collections
     */
    public static boolean initializeUserSession(String uid, String email) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            
            String fullName = null;
            String flatNo = null;
            String role = null;
            
            // First, check in 'admins' collection
            DocumentSnapshot adminDoc = db.collection("admins").document(uid).get().get();
            
            if (adminDoc.exists()) {
                fullName = adminDoc.getString("fullName");
                flatNo = adminDoc.getString("flatNo");
                role = "admin";
                System.out.println("User found in admins collection: " + fullName);
            } else {
                // Check in 'users' collection (residents)
                DocumentSnapshot userDoc = db.collection("users").document(uid).get().get();
                
                if (userDoc.exists()) {
                    fullName = userDoc.getString("fullName");
                    flatNo = userDoc.getString("flatNo");
                    role = userDoc.getString("role");
                    
                    // Default to resident if role not specified
                    if (role == null || role.isEmpty()) {
                        role = "resident";
                    }
                    System.out.println("User found in users collection: " + fullName);
                } else {
                    // User authenticated but not found in database - treat as new user
                    System.out.println("User not found in database, creating with email: " + email);
                    fullName = email.split("@")[0]; // Use email prefix as name
                    flatNo = "Unknown";
                    role = "resident";
                }
            }
            
            // Initialize the session
            UserSession session = UserSession.getInstance();
            session.initSession(uid, email, fullName, flatNo, role);
            
            System.out.println("Session initialized: " + session.toString());
            return true;
            
        } catch (Exception e) {
            System.err.println("Error initializing user session: " + e.getMessage());
            e.printStackTrace();
            
            // Still initialize a basic session with email
            UserSession session = UserSession.getInstance();
            session.initSession(uid, email, email.split("@")[0], "Unknown", "resident");
            
            return false;
        }
    }
    
    /**
     * Fetch user data from Firestore by UID
     */
    public static JSONObject fetchUserData(String uid) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            
            // Check admins collection first
            DocumentSnapshot adminDoc = db.collection("admins").document(uid).get().get();
            if (adminDoc.exists()) {
                JSONObject userData = new JSONObject();
                userData.put("fullName", adminDoc.getString("fullName"));
                userData.put("email", adminDoc.getString("email"));
                userData.put("flatNo", adminDoc.getString("flatNo"));
                userData.put("role", "admin");
                userData.put("collection", "admins");
                return userData;
            }
            
            // Check users collection
            DocumentSnapshot userDoc = db.collection("users").document(uid).get().get();
            if (userDoc.exists()) {
                JSONObject userData = new JSONObject();
                userData.put("fullName", userDoc.getString("fullName"));
                userData.put("email", userDoc.getString("email"));
                userData.put("flatNo", userDoc.getString("flatNo"));
                userData.put("role", userDoc.getString("role") != null ? userDoc.getString("role") : "resident");
                userData.put("collection", "users");
                return userData;
            }
            
            return null;
            
        } catch (Exception e) {
            System.err.println("Error fetching user data: " + e.getMessage());
            return null;
        }
    }
}
