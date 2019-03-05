package com.example.healthapp.util;

public class Nutrients {
    double carbs;
    double calories;

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public Nutrients(double carbs, double calories) {
        this.carbs = carbs;
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "Nutrients{" +
                "carbs=" + carbs +
                ", calories=" + calories +
                '}';
    }
}
