package com.example.healthapp.classes;

public class Treatment {
    private Integer carbsperDay;
    private String dayTreatmentName;
    private String nightTreatmentName;

    public Treatment() {
    }

    public Treatment(Integer carbsperDay, String dayTreatmentName, String nightTreatmentName) {
        this.carbsperDay = carbsperDay;
        this.dayTreatmentName = dayTreatmentName;
        this.nightTreatmentName = nightTreatmentName;
    }

    public Integer getCarbsperDay() {
        return carbsperDay;
    }

    public void setCarbsperDay(Integer carbsperDay) {
        this.carbsperDay = carbsperDay;
    }


    public String getDayTreatmentName() {
        return dayTreatmentName;
    }

    public void setDayTreatmentName(String dayTreatmentName) {
        this.dayTreatmentName = dayTreatmentName;
    }

    public String getNightTreatmentName() {
        return nightTreatmentName;
    }

    public void setNightTreatmentName(String nightTreatmentName) {
        this.nightTreatmentName = nightTreatmentName;
    }

    @Override
    public String toString() {
        return "Treatment{" +
                "carbsperDay=" + carbsperDay +
                ", dayTreatmentName='" + dayTreatmentName + '\'' +
                ", nightTreatmentName='" + nightTreatmentName + '\'' +
                '}';
    }
}
