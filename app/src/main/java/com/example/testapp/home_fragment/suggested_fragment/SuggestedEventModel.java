package com.example.testapp.home_fragment.suggested_fragment;

import com.example.testapp.home_fragment.EventModel;

import java.time.ZonedDateTime;

public class SuggestedEventModel extends EventModel {

    String eventType = EventModel.SUGGESTED;




    public SuggestedEventModel(ZonedDateTime eventStartTimeInDateTime, ZonedDateTime eventEndTimeInDateTime, String eventHeader, String eventDescription, String eventID, Boolean eventOwnedByUser) {
        super(eventStartTimeInDateTime, eventEndTimeInDateTime, eventHeader, eventDescription, eventID, eventOwnedByUser);

    }


    @Override
    public String getEventType(){
        return eventType;
    }
}

