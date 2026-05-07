package com.login.Model;

public class SignupModel {
    private String fullName;
    private String email;
    private String flatNo;
    private String password;
    private String confirmPassword;

    public SignupModel(String fullName, String email, String flatNo, String password, String confirmPassword) {
        this.fullName = fullName;
        this.email = email;
        this.flatNo = flatNo;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getFlatNo() { return flatNo; }
    public String getPassword() { return password; }
    public String getConfirmPassword() { return confirmPassword; }

    public boolean isPasswordMatching() {
        return password != null && password.equals(confirmPassword);
    }
}
