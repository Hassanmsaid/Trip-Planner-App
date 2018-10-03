package com.example.lenovo.finalgp_test1.TripPlannerClasses;

public class RoundFlightClass {

    private int flight_id, flight_num, business_seats, economy_seats,
            business_price, economy_price ;
    private String flying_from, flying_to , airways, flight_date_from ,flight_date_to , departure, arrival;

    public RoundFlightClass(){}

    public RoundFlightClass(int flight_id, int flight_num, String flying_from, String flying_to, int business_seats, int economy_seats, int business_price, int economy_price, String airways, String flight_date_from , String flight_date_to, String departure, String arrival) {
        this.flight_id = flight_id;
        this.flight_num = flight_num;
        this.flying_from = flying_from;
        this.flying_to = flying_to;
        this.business_seats = business_seats;
        this.economy_seats = economy_seats;
        this.business_price = business_price;
        this.economy_price = economy_price;
        this.airways = airways;
        this.flight_date_from = flight_date_from;
        this.flight_date_to = flight_date_to;
        this.departure = departure;
        this.arrival = arrival;
    }

    public int getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(int flight_id) {
        this.flight_id = flight_id;
    }

    public int getFlight_num() {
        return flight_num;
    }

    public void setFlight_num(int flight_num) {
        this.flight_num = flight_num;
    }

    public String getFlying_from() {
        return flying_from;
    }

    public void setFlying_from(String flying_from) {
        this.flying_from = flying_from;
    }

    public String getFlying_to() {
        return flying_to;
    }

    public void setFlying_to(String flying_to) {
        this.flying_to = flying_to;
    }

    public int getBusiness_seats() {
        return business_seats;
    }

    public void setBusiness_seats(int business_seats) {
        this.business_seats = business_seats;
    }

    public int getEconomy_seats() {
        return economy_seats;
    }

    public void setEconomy_seats(int economy_seats) {
        this.economy_seats = economy_seats;
    }

    public int getBusiness_price() {
        return business_price;
    }

    public void setBusiness_price(int business_price) {
        this.business_price = business_price;
    }

    public int getEconomy_price() {
        return economy_price;
    }

    public void setEconomy_price(int economy_price) {
        this.economy_price = economy_price;
    }

    public String getAirways() {
        return airways;
    }

    public void setAirways(String airways) {
        this.airways = airways;
    }

    public String getFlight_date_from() {
        return flight_date_from;
    }

    public void setFlight_date_from(String flight_date_from) {
        this.flight_date_from = flight_date_from;
    }

    public String getFlight_date_to() {
        return flight_date_to;
    }

    public void setFlight_date_to(String flight_date_to) {
        this.flight_date_to = flight_date_to;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }
}
