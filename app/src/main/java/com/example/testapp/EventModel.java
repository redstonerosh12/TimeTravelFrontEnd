package com.example.testapp;

public abstract class EventModel {
    public static final String CONCRETE = "CONCRETE";
    public static final String VOTING = "VOTING";
    public static final String SUGGESTED = "SUGGESTED";

    String eventTime;
    String eventHeader;
    String eventDescription;
    String eventID;

    Boolean eventOwnedByUser;

    public EventModel(String eventTime, String eventHeader, String eventDescription, String eventID, Boolean eventOwnedByUser) {
        this.eventTime = eventTime;
        this.eventHeader = eventHeader;
        this.eventDescription = eventDescription;
        this.eventID = eventID;
        this.eventOwnedByUser = eventOwnedByUser;
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

    public String getEventID() {
        return eventID;
    }

    public Boolean getEventOwnedByUser(){
        return eventOwnedByUser;
    }
    abstract String getEventType();
}
