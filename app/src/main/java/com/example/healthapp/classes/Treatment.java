package com.example.healthapp.classes;

public class Treatment {
    private Integer numebrOfMeals;
    private Integer numberOfSnacks;
    private Integer numberOfInjections;
    private Integer carbsPerDay;

    public Treatment(Integer numebrOfMeals, Integer numberOfSnacks, Integer numberOfInjections, Integer carbsPerDay) {
        this.numebrOfMeals = numebrOfMeals;
        this.numberOfSnacks = numberOfSnacks;
        this.numberOfInjections = numberOfInjections;
        this.carbsPerDay = carbsPerDay;
    }

    public Integer getCarbsPerDay() {
        return carbsPerDay;
    }

    public void setCarbsPerDay(Integer carbsPerDay) {
        this.carbsPerDay = carbsPerDay;
    }

    public Integer getNumebrOfMeals() {
        return numebrOfMeals;
    }

    public void setNumebrOfMeals(Integer numebrOfMeals) {
        this.numebrOfMeals = numebrOfMeals;
    }

    public Integer getNumberOfSnacks() {
        return numberOfSnacks;
    }

    public void setNumberOfSnacks(Integer numberOfSnacks) {
        this.numberOfSnacks = numberOfSnacks;
    }

    public Integer getNumberOfInjections() {
        return numberOfInjections;
    }

    public void setNumberOfInjections(Integer numberOfInjections) {
        this.numberOfInjections = numberOfInjections;
    }
}
