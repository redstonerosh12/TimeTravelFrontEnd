package com.example.testapp.api;

import com.example.testapp.model.EventModel;

import java.time.LocalDate;
import java.util.ArrayList;

public class DataSource {
    public static void getEventsByDate(String travelPlanId, LocalDate date, EventModel.Status status, API.Callback<ArrayList<EventModel>> callback) {
        API.APIBuilder<ArrayList<EventModel.GET>> api;
        if (status == EventModel.Status.CONCRETE) {
            api = API.TravelPlans.getConcreteEvents(travelPlanId, date);
        } else {
            api = API.TravelPlans.getSuggestedEvents(travelPlanId, date);
        }
        api.setOnResponse(eventsGet -> {
                    ArrayList<EventModel> events = new ArrayList<>();
                    for (EventModel.GET eventGet : eventsGet) {
                        EventModel event = eventGet.getEvent();
                        if (event.getPlaceStatus().equals(status) && event.getDate().equals(date)) {
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
