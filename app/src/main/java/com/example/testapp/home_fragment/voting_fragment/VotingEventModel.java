package com.example.testapp.home_fragment.voting_fragment;

import com.example.testapp.home_fragment.EventModel;

import java.time.ZonedDateTime;

public class VotingEventModel extends EventModel {

    String eventType = EventModel.CONCRETE;
    Boolean eventVotedOnByUser;




    public VotingEventModel(ZonedDateTime eventStartTimeInDateTime, ZonedDateTime eventEndTimeInDateTime, String eventHeader, String eventDescription, String eventID, Boolean eventOwnedByUser, Boolean eventVotedOnByUser) {
        super(eventStartTimeInDateTime, eventEndTimeInDateTime, eventHeader, eventDescription, eventID, eventOwnedByUser);
        this.eventVotedOnByUser = eventVotedOnByUser;

    }


    @Override
    public String getEventType(){
        return eventType;
    }

    public Boolean getEventVotedOnByUser(){
        return eventVotedOnByUser;
    }
}
