package com.login.Controller;

import com.login.Model.SigninModel;
import com.login.Utils.SigninFirebaseServices;
import com.login.Utils.UserSession;
import org.json.JSONObject;

public class SigninController {
    public static String loginUser(SigninModel user) {
        if (user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            return "Please fill in all fields.";
        }

        JSONObject response = SigninFirebaseServices.authenticateUser(user.getEmail(), user.getPassword());

        if (response.getString("status").equals("success")) {
            // Session is already set in SigninFirebaseServices
            // Log the successful login with user details
            UserSession session = UserSession.getInstance();
            System.out.println("SigninController: User logged in successfully - " + session.toString());
            return "success";
        } else {
            return response.getString("message");
        }
    }
    
    /**
     * Gets the currently logged-in user's UID.
     * @return The user UID or null if no user is logged in.
     */
    public static String getCurrentUserUid() {
        UserSession session = UserSession.getInstance();
        return session.getUserUid();
    }
    
    /**
     * Checks if a user is currently logged in.
     * @return true if logged in, false otherwise.
     */
    public static boolean isUserLoggedIn() {
        return UserSession.getInstance().isLoggedIn();
    }
    
    /**
     * Logs out the current user.
     */
    public static void logoutUser() {
        UserSession.getInstance().clearSession();
        System.out.println("SigninController: User logged out");
    }
}
