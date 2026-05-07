package com.login.Utils;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.json.JSONObject;

public class SigninFirebaseServices {
    private static final String FIREBASE_API_KEY = "AIzaSyD25nvpaX39IaU1d7hTw2qr9sm26W0tr3U";  // replace with actual Firebase Web API key

    public static JSONObject authenticateUser(String email, String password) {
        String url = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + FIREBASE_API_KEY;

        JSONObject body = new JSONObject();
        body.put("email", email);
        body.put("password", password);
        body.put("returnSecureToken", true);

        HttpResponse<JsonNode> response = Unirest.post(url)
                .header("Content-Type", "application/json")
                .body(body.toString())
                .asJson();

        if (response.getStatus() == 200) {
            JSONObject responseData = response.getBody().getObject();
            
            // Extract user data from Firebase response
            String userUid = responseData.optString("localId", "");
            String userEmail = responseData.optString("email", "");
            String idToken = responseData.optString("idToken", "");
            String refreshToken = responseData.optString("refreshToken", "");
            String displayName = responseData.optString("displayName", "");
            
            // Store user session data
            UserSession session = UserSession.getInstance();
            session.setSession(userUid, userEmail, idToken, refreshToken);
            if (displayName != null && !displayName.isEmpty()) {
                session.setDisplayName(displayName);
            }
            
            System.out.println("SigninFirebaseServices: Login successful - User UID: " + userUid);
            
            return new JSONObject()
                .put("status", "success")
                .put("data", responseData)
                .put("userUid", userUid)
                .put("email", userEmail);
        } else {
            return new JSONObject()
                .put("status", "error")
                .put("message", response.getBody().getObject().optString("error", "Login failed"));
        }
    }
}
