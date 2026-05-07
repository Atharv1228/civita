package com.login.Utils;

/**
 * Singleton class to manage user session data across the application.
 * Stores the logged-in user's information and provides access to it from any page.
 */
public class UserSession {
    private static UserSession instance;
    
    private String userUid;
    private String email;
    private String displayName;
    private String idToken;
    private String refreshToken;
    private String flatNo;
    private boolean isAdmin;
    
    // Private constructor to prevent direct instantiation
    private UserSession() {}
    
    /**
     * Gets the singleton instance of UserSession.
     * @return The UserSession instance
     */
    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }
    
    /**
     * Sets up the session with user data from Firebase authentication.
     * @param userUid The Firebase User ID (localId)
     * @param email The user's email address
     * @param idToken The Firebase ID token
     * @param refreshToken The Firebase refresh token
     */
    public void setSession(String userUid, String email, String idToken, String refreshToken) {
        this.userUid = userUid;
        this.email = email;
        this.idToken = idToken;
        this.refreshToken = refreshToken;
        System.out.println("UserSession: Session set for user - UID: " + userUid + ", Email: " + email);
    }
    
    /**
     * Clears the current session (for logout).
     */
    public void clearSession() {
        this.userUid = null;
        this.email = null;
        this.displayName = null;
        this.idToken = null;
        this.refreshToken = null;
        this.flatNo = null;
        this.isAdmin = false;
        System.out.println("UserSession: Session cleared");
    }
    
    /**
     * Checks if a user is currently logged in.
     * @return true if a user is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return userUid != null && !userUid.isEmpty();
    }
    
    // Getters and Setters
    
    public String getUserUid() {
        return userUid;
    }
    
    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    public String getIdToken() {
        return idToken;
    }
    
    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
    
    public String getRefreshToken() {
        return refreshToken;
    }
    
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    
    public String getFlatNo() {
        return flatNo;
    }
    
    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }
    
    public boolean isAdmin() {
        return isAdmin;
    }
    
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
    
    @Override
    public String toString() {
        return "UserSession{" +
                "userUid='" + userUid + '\'' +
                ", email='" + email + '\'' +
                ", displayName='" + displayName + '\'' +
                ", flatNo='" + flatNo + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
