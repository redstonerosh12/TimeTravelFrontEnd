package com.example.testapp.home_fragment.suggested_fragment;

import com.example.testapp.home_fragment.EventModel;

public class SuggestedEventModel extends EventModel {

    String eventType = EventModel.SUGGESTED;




    public SuggestedEventModel(String eventTime, String eventHeader, String eventDescription, String eventID, Boolean eventOwnedByUser) {
        super(eventTime, eventHeader, eventDescription, eventID, eventOwnedByUser);

    }


    @Override
    public String getEventType(){
        return eventType;
    }
}

