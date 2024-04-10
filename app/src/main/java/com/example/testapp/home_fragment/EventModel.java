package com.example.testapp.home_fragment;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public abstract class EventModel {
    public static final String CONCRETE = "CONCRETE";
    public static final String VOTING = "VOTING";
    public static final String SUGGESTED = "SUGGESTED";

    public ZonedDateTime eventStartTimeInDateTime;
    public ZonedDateTime eventEndTimeInDateTime;
    public String eventTime;
    public String eventHeader;
    public String eventDescription;
    public String eventID;

    public Boolean eventOwnedByUser;

    public EventModel(ZonedDateTime eventStartTimeInDateTime, ZonedDateTime eventEndTimeInDateTime, String eventHeader, String eventDescription, String eventID, Boolean eventOwnedByUser) {
        this.eventStartTimeInDateTime = eventStartTimeInDateTime;
        this.eventEndTimeInDateTime = eventEndTimeInDateTime;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        String formattedStartTime = formatter.format(this.eventStartTimeInDateTime);
        String formattedEndTime = formatter.format(this.eventEndTimeInDateTime);


        this.eventTime = formattedStartTime + "-"+ formattedEndTime;
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

    public ZonedDateTime getEventStartTimeInDateTime(){ return eventStartTimeInDateTime; }
    public ZonedDateTime getEventEndTimeInDateTime(){ return eventEndTimeInDateTime; }
    public abstract String getEventType();
}
