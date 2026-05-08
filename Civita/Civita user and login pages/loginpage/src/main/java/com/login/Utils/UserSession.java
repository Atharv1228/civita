package com.login.Utils;

/**
 * UserSession - Singleton class to manage the currently logged-in user's session.
 * This class stores user information after successful authentication and provides
 * access to user data throughout the application.
 */
public class UserSession {
    
    private static UserSession instance;
    
    // User information
    private String uid;
    private String email;
    private String fullName;
    private String flatNo;
    private String role; // "admin", "resident", or "guest"
    private boolean isLoggedIn;
    
    // Private constructor for singleton pattern
    private UserSession() {
        clearSession();
    }
    
    /**
     * Get the singleton instance of UserSession
     */
    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }
    
    /**
     * Initialize a session after successful login
     */
    public void initSession(String uid, String email, String fullName, String flatNo, String role) {
        this.uid = uid;
        this.email = email;
        this.fullName = fullName;
        this.flatNo = flatNo;
        this.role = role != null ? role.toLowerCase() : "resident";
        this.isLoggedIn = true;
        
        System.out.println("Session initialized for user: " + fullName + " with role: " + this.role);
    }
    
    /**
     * Clear the current session (logout)
     */
    public void clearSession() {
        this.uid = null;
        this.email = null;
        this.fullName = null;
        this.flatNo = null;
        this.role = null;
        this.isLoggedIn = false;
        
        System.out.println("Session cleared");
    }
    
    // Getters
    public String getUid() {
        return uid;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public String getFlatNo() {
        return flatNo;
    }
    
    public String getRole() {
        return role;
    }
    
    public boolean isLoggedIn() {
        return isLoggedIn;
    }
    
    /**
     * Check if the current user is an admin
     */
    public boolean isAdmin() {
        return "admin".equalsIgnoreCase(role);
    }
    
    /**
     * Check if the current user is a resident
     */
    public boolean isResident() {
        return "resident".equalsIgnoreCase(role);
    }
    
    /**
     * Check if the current user is a guest
     */
    public boolean isGuest() {
        return "guest".equalsIgnoreCase(role);
    }
    
    // Setters for updating profile information
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
        return "UserSession{" +
                "uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", flatNo='" + flatNo + '\'' +
                ", role='" + role + '\'' +
                ", isLoggedIn=" + isLoggedIn +
                '}';
    }
}
