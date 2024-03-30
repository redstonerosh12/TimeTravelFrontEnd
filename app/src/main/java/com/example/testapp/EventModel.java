package com.example.testapp;

public class EventModel {
    String eventTime;
    String eventHeader;
    String eventDescription;


    public EventModel(String eventTime, String eventHeader, String eventDescription) {
        this.eventTime = eventTime;
        this.eventHeader = eventHeader;
        this.eventDescription = eventDescription;
    }


    public String getEventTime() {
        return eventTime;
    }

    public String getEventHeader() {
        return eventHeader;
    }

    public String getEventDescription() {
        return eventDescription;
    }
}
