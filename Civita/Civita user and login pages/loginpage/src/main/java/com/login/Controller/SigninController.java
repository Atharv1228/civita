package com.login.Controller;

import com.login.Model.SigninModel;
import com.login.Utils.SigninFirebaseServices;
import com.login.Utils.UserSession;
import org.json.JSONObject;

public class SigninController {
    
    /**
     * Login user and initialize session with user data
     */
    public static String loginUser(SigninModel user) {
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return "Please enter your email address.";
        }
        
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return "Please enter your password.";
        }

        JSONObject response = SigninFirebaseServices.authenticateUser(user.getEmail(), user.getPassword());

        if (response.getString("status").equals("success")) {
            // Get the user data from response
            JSONObject data = response.getJSONObject("data");
            String localId = data.optString("localId", ""); // This is the Firebase UID
            
            // Fetch additional user data and initialize session
            boolean sessionInitialized = SigninFirebaseServices.initializeUserSession(localId, user.getEmail());
            
            if (!sessionInitialized) {
                System.err.println("Warning: Could not fully initialize user session, but login was successful");
            }
            
            return "success";
        } else {
            // Parse error message for better user feedback
            String errorMessage = response.optString("message", "Login failed");
            
            if (errorMessage.contains("INVALID_PASSWORD") || errorMessage.contains("INVALID_LOGIN_CREDENTIALS")) {
                return "Invalid email or password. Please try again.";
            } else if (errorMessage.contains("EMAIL_NOT_FOUND")) {
                return "No account found with this email. Please sign up first.";
            } else if (errorMessage.contains("TOO_MANY_ATTEMPTS")) {
                return "Too many failed attempts. Please try again later.";
            } else if (errorMessage.contains("USER_DISABLED")) {
                return "This account has been disabled. Please contact support.";
            }
            
            return errorMessage;
        }
    }
    
    /**
     * Logout the current user
     */
    public static void logoutUser() {
        UserSession.getInstance().clearSession();
        System.out.println("User logged out successfully");
    }
    
    /**
     * Check if a user is currently logged in
     */
    public static boolean isUserLoggedIn() {
        return UserSession.getInstance().isLoggedIn();
    }
    
    /**
     * Get the current user's role
     */
    public static String getCurrentUserRole() {
        return UserSession.getInstance().getRole();
    }
    
    /**
     * Check if current user is an admin
     */
    public static boolean isCurrentUserAdmin() {
        return UserSession.getInstance().isAdmin();
    }
}
