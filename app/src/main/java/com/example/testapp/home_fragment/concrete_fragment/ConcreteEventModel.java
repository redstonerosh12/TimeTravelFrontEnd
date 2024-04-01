package com.example.testapp.home_fragment.concrete_fragment;

import com.example.testapp.home_fragment.EventModel;

public class ConcreteEventModel extends EventModel {


    String eventType = EventModel.CONCRETE;




    public ConcreteEventModel(String eventTime, String eventHeader, String eventDescription, String eventID, Boolean eventOwnedByUser) {
        super(eventTime, eventHeader, eventDescription, eventID, eventOwnedByUser);

    }


    @Override
    public String getEventType(){
        return eventType;
    }
}
