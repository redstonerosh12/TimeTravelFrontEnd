package com.example.testapp.api;

import com.example.testapp.middleware.Auth;
import com.example.testapp.model.EventModel;

import java.time.LocalDate;
import java.util.ArrayList;

public class DataSource {
    public static void getEventsByDate(String travelPlanId, LocalDate date, EventModel.Status status, API.Callback<ArrayList<EventModel>> callback) {
        API.TravelPlans.getTravelPlan(Auth.getInstance(), travelPlanId)
                .setOnResponse(travelPlan -> callback.onResponse(travelPlan.getEvents()))
                .setOnFailure(res -> {
                }).fetch();
    }
}
