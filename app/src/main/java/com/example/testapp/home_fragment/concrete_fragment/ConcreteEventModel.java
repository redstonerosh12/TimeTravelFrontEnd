package com.example.testapp.home_fragment.concrete_fragment;

import com.example.testapp.home_fragment.EventModel;

import java.time.ZonedDateTime;

public class ConcreteEventModel extends EventModel {


    String eventType = EventModel.CONCRETE;




    public ConcreteEventModel(ZonedDateTime eventStartTimeInDateTime, ZonedDateTime eventEndTimeInDateTime, String eventHeader, String eventDescription, String eventID, Boolean eventOwnedByUser) {
        super(eventStartTimeInDateTime, eventEndTimeInDateTime, eventHeader, eventDescription, eventID, eventOwnedByUser);

    }


    @Override
    public String getEventType(){
        return eventType;
    }
}
