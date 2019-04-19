package com.example.healthapp.classes;

import java.util.Date;

public class User {
    private String firstName;
    private String lastNAme;
    private Date date;
    private double weight;
    private double height;
    private String gender;
    private String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public User(String firstName, String lastNAme, Date date, double weight, double height, String gender) {
        this.firstName = firstName;
        this.lastNAme = lastNAme;
        this.date = date;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastNAme() {
        return lastNAme;
    }

    public void setLastNAme(String lastNAme) {
        this.lastNAme = lastNAme;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastNAme='" + lastNAme + '\'' +
                ", date=" + date +
                ", weight=" + weight +
                ", height=" + height +
                ", gender='" + gender + '\'' +
                '}';
    }
}
