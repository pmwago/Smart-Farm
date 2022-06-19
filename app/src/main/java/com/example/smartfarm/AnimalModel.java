package com.example.smartfarm;

import java.util.Date;

public class AnimalModel {
    String name;
    String email;
    String date;
    double productQty;
    double productPrice;
    double dailyCash;

    public AnimalModel(String name, String email, String date, double productQty, double productPrice, double dailyCash) {
        this.name = name;
        this.email = email;
        this.date = date;
        this.productQty = productQty;
        this.productPrice = productPrice;
        this.dailyCash = dailyCash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getProductQty() {
        return productQty;
    }

    public void setProductQty(double productQty) {
        this.productQty = productQty;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getDailyCash() {
        return dailyCash;
    }

    public void setDailyCash(double dailyCash) {
        this.dailyCash = dailyCash;
    }
}
