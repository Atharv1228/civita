package com.login.Model;


public class UserProfile {
    private String fullName;
    private String email;
    private String flatNo;

    // Default constructor (required for Firebase)
    public UserProfile() {
    }

    public UserProfile(String fullName, String email, String flatNo) {
        this.fullName = fullName;
        this.email = email;
        this.flatNo = flatNo;
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

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }
}