package com.example.testapp.profile_fragment;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TripModel {

    private String country;
    private String startDate;
    private String endDate;

    public TripModel(String country, String startDate, String endDate) {
        this.country = country;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getCountry() {
        return country;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}