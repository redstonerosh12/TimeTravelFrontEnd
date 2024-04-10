package com.example.testapp.model;

import java.util.ArrayList;

public class Voting {
    private String id;
    private String creator;
    private String startTime;
    private String endTime;
    private EventModel event;
    private ArrayList<Vote> votes;

    public class Vote {
        private String creator;
        private String description;
        private boolean isLiked;
    }
}
