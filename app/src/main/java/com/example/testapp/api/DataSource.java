package com.example.testapp.api;

import com.example.testapp.model.EventModel;

import java.time.LocalDate;
import java.util.ArrayList;

public class DataSource {
    public static void getEventsByDate(String travelPlanId, LocalDate date, EventModel.Status status, API.Callback<ArrayList<EventModel.GET>> callback) {
        API.APIBuilder<ArrayList<EventModel.GET>> api;
        if (status == EventModel.Status.CONCRETE) {
            api = API.TravelPlans.getConcreteEvents(travelPlanId, date);
        } else {
            api = API.TravelPlans.getSuggestedEvents(travelPlanId, date);
        }
        api.setOnResponse(callback)
                .setOnFailure(res -> {
                }).fetch();
    }
}
