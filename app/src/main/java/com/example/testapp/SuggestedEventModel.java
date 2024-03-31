package com.example.testapp;

public class SuggestedEventModel extends EventModel{

    String eventType = EventModel.SUGGESTED;




    public SuggestedEventModel(String eventTime, String eventHeader, String eventDescription, String eventID, Boolean eventOwnedByUser) {
        super(eventTime, eventHeader, eventDescription, eventID, eventOwnedByUser);

    }


    @Override
    public String getEventType(){
        return eventType;
    }
}

