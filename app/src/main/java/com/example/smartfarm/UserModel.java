package com.example.smartfarm;

public class UserModel {
    String email;
    String password;
    String userRole;
    String livestock;

    public UserModel(String email, String password, String userRole, String livestock) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.livestock = livestock;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getLivestock() {
        return livestock;
    }

    public void setLivestock(String livestock) {
        this.livestock = livestock;
    }

    public UserModel() {
    }



}
