package com.example.healthapp.classes;

public class DailyTreatment {
    private Integer bloodSugarLevel;
    private Integer nrOfCarbs;
    private double nrOfShots;
    private String timeOfTheDay;

    public DailyTreatment(Integer bloodSugarLevel, Integer nrOfCarbs, double nrOfShots, String timeOfTheDay) {
        this.bloodSugarLevel = bloodSugarLevel;
        this.nrOfCarbs = nrOfCarbs;
        this.nrOfShots = nrOfShots;
        this.timeOfTheDay = timeOfTheDay;
    }

    public DailyTreatment(Integer bloodSugarLevel, Integer nrOfCarbs, double nrOfShots) {
        this.bloodSugarLevel = bloodSugarLevel;
        this.nrOfCarbs = nrOfCarbs;
        this.nrOfShots = nrOfShots;
    }

    public Integer getBloodSugarLevel() {
        return bloodSugarLevel;
    }

    public void setBloodSugarLevel(Integer bloodSugarLevel) {
        this.bloodSugarLevel = bloodSugarLevel;
    }

    public Integer getNrOfCarbs() {
        return nrOfCarbs;
    }

    public void setNrOfCarbs(Integer nrOfCarbs) {
        this.nrOfCarbs = nrOfCarbs;
    }

    public double getNrOfShots() {
        return nrOfShots;
    }

    public void setNrOfShots(double nrOfShots) {
        this.nrOfShots = nrOfShots;
    }

    public String getTimeOfTheDay() {
        return timeOfTheDay;
    }

    public void setTimeOfTheDay(String timeOfTheDay) {
        this.timeOfTheDay = timeOfTheDay;
    }
}
