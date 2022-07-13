package com.example.smartfarm;

public class AnimalInputs {
    String role; String cost;String TheDate;String inputDetails;

    public AnimalInputs(String role, String cost, String theDate, String inputDetails) {
        this.role = role;
        this.cost = cost;
        TheDate = theDate;
        this.inputDetails = inputDetails;
    }

    public String getRole() {
        return role;
    }

    public String getCost() {
        return cost;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTheDate() {
        return TheDate;
    }

    public void setTheDate(String theDate) {
        TheDate = theDate;
    }

    public String getInputDetails() {
        return inputDetails;
    }

    public void setInputDetails(String inputDetails) {
        this.inputDetails = inputDetails;
    }
}
