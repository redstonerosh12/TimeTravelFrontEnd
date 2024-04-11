package com.example.testapp.api;

import com.example.testapp.middleware.Auth;
import com.example.testapp.model.EventModel;

import java.time.LocalDate;
import java.util.ArrayList;

public class DataSource {
    public static void getEventsByDate(String travelPlanId, LocalDate date, EventModel.Status status, API.Callback<ArrayList<EventModel>> callback) {
        API.TravelPlans.getTravelPlan(Auth.getInstance(), travelPlanId)
                .setOnResponse(travelPlan -> {
                    ArrayList<EventModel> events = new ArrayList<>();
                    for(EventModel event: travelPlan.getEvents()) {
                        if(event.getPlaceStatus().equals(status) && event.getDate().equals(date)) {
                            events.add(event);
                        }
                    }
                    events.sort(new EventModel.SortbyTime()); //FIXME: Might not need if server return sorted
                    callback.onResponse(events);
                })
                .setOnFailure(res -> {
                }).fetch();
    }
}
