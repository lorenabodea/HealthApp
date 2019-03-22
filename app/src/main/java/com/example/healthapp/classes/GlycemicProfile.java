package com.example.healthapp.classes;

public class GlycemicProfile {
    String timeofTheDay;
    private boolean beforeMeal;
    Integer bloodSugarLevel;

    public GlycemicProfile(String timeofTheDay, boolean beforeMeal, Integer bloodSugarLevel) {
        this.timeofTheDay = timeofTheDay;
        this.beforeMeal = beforeMeal;
        this.bloodSugarLevel = bloodSugarLevel;
    }

    public String getTimeofTheDay() {
        return timeofTheDay;
    }

    public void setTimeofTheDay(String timeofTheDay) {
        this.timeofTheDay = timeofTheDay;
    }

    public boolean isBeforeMeal() {
        return beforeMeal;
    }

    public void setBeforeMeal(boolean beforeMeal) {
        this.beforeMeal = beforeMeal;
    }

    public Integer getBloodSugarLevel() {
        return bloodSugarLevel;
    }

    public void setBloodSugarLevel(Integer bloodSugarLevel) {
        this.bloodSugarLevel = bloodSugarLevel;
    }

    @Override
    public String toString() {
        return "GlycemicProfile{" +
                "timeofTheDay='" + timeofTheDay + '\'' +
                ", beforeMeal=" + beforeMeal +
                ", bloodSugarLevel=" + bloodSugarLevel +
                '}';
    }
}
