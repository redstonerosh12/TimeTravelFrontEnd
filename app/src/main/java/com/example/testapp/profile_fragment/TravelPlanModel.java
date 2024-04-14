package com.example.testapp.profile_fragment;

import com.example.testapp.model.EventModel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TravelPlanModel {

    private String country;
    private String startDate;
    private String endDate;

    public TravelPlanModel(String country, String startDate, String endDate) {
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

    public TravelPlanModel getTravelPlan() {
        return new TravelPlanModel(
                country,
                startDate,
                endDate);
    }

    public static class CreateTravelPlan {
        private String travelPlanTitle;
        private String startDate;
        private String endDate;

        public CreateTravelPlan(String travelPlanTitle, String startDate, String endDate) {
            this.travelPlanTitle = travelPlanTitle;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }
}