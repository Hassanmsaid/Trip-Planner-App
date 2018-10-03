package com.example.lenovo.finalgp_test1.TripPlannerClasses;

import java.util.List;

public class DayClass {
    private String date;
    private List<ToursClass> toursPerDay;

    public DayClass(){

    }

    public DayClass(String date, List<ToursClass> toursPerDay) {
        this.date = date;
        this.toursPerDay = toursPerDay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ToursClass> getToursPerDay() {
        return toursPerDay;
    }

    public void setToursPerDay(List<ToursClass> toursPerDay) {
//        for(int i=0; i<toursPerDay.size()/2; i++){
//            toursPerDay.remove(0);
//        }
        this.toursPerDay = toursPerDay;
    }
}
