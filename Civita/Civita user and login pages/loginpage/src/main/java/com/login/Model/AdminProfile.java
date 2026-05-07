package com.login.Model;

public class AdminProfile {
    private String fullName;
    private String email;

    // Default constructor (required for Firebase)
    public AdminProfile() {
    }

    public AdminProfile(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}