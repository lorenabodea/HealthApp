package com.example.healthapp.classes;

public class Treatment {
    private Integer carbsperDay;
    private Integer carbsMeal1;
    private Integer carbsMeal2;
    private Integer carbsMeal3;
    private Integer carbsMeal4;
    private Double shot1;
    private Double shot2;
    private Double shot3;
    private Double shot4;
    private String dayTreatmentName;
    private String nightTreatmentName;

    public Treatment(Integer carbsperDay, Integer carbsMeal1, Integer carbsMeal2, Integer carbsMeal3, Integer carbsMeal4, Double shot1, Double shot2, Double shot3, Double shot4, String dayTreatmentName, String nightTreatmentName) {
        this.carbsperDay = carbsperDay;
        this.carbsMeal1 = carbsMeal1;
        this.carbsMeal2 = carbsMeal2;
        this.carbsMeal3 = carbsMeal3;
        this.carbsMeal4 = carbsMeal4;
        this.shot1 = shot1;
        this.shot2 = shot2;
        this.shot3 = shot3;
        this.shot4 = shot4;
        this.dayTreatmentName = dayTreatmentName;
        this.nightTreatmentName = nightTreatmentName;
    }

    public Integer getCarbsperDay() {
        return carbsperDay;
    }

    public void setCarbsperDay(Integer carbsperDay) {
        this.carbsperDay = carbsperDay;
    }

    public Integer getCarbsMeal1() {
        return carbsMeal1;
    }

    public void setCarbsMeal1(Integer carbsMeal1) {
        this.carbsMeal1 = carbsMeal1;
    }

    public Integer getCarbsMeal2() {
        return carbsMeal2;
    }

    public void setCarbsMeal2(Integer carbsMeal2) {
        this.carbsMeal2 = carbsMeal2;
    }

    public Integer getCarbsMeal3() {
        return carbsMeal3;
    }

    public void setCarbsMeal3(Integer carbsMeal3) {
        this.carbsMeal3 = carbsMeal3;
    }

    public Integer getCarbsMeal4() {
        return carbsMeal4;
    }

    public void setCarbsMeal4(Integer carbsMeal4) {
        this.carbsMeal4 = carbsMeal4;
    }

    public Double getShot1() {
        return shot1;
    }

    public void setShot1(Double shot1) {
        this.shot1 = shot1;
    }

    public Double getShot2() {
        return shot2;
    }

    public void setShot2(Double shot2) {
        this.shot2 = shot2;
    }

    public Double getShot3() {
        return shot3;
    }

    public void setShot3(Double shot3) {
        this.shot3 = shot3;
    }

    public Double getShot4() {
        return shot4;
    }

    public void setShot4(Double shot4) {
        this.shot4 = shot4;
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
                ", carbsMeal1=" + carbsMeal1 +
                ", carbsMeal2=" + carbsMeal2 +
                ", carbsMeal3=" + carbsMeal3 +
                ", carbsMeal4=" + carbsMeal4 +
                ", shot1=" + shot1 +
                ", shot2=" + shot2 +
                ", shot3=" + shot3 +
                ", shot4=" + shot4 +
                ", dayTreatmentName='" + dayTreatmentName + '\'' +
                ", nightTreatmentName='" + nightTreatmentName + '\'' +
                '}';
    }
}
