package com.example.testapp.model;

import android.util.Log;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class EventModel {
    private String id;
    private String creator;
    private String title;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private String description;
    private Status placeStatus;
    private String location;

    public EventModel(String id, String creator, String title, ZonedDateTime startTime, ZonedDateTime endTime, String description, Status placeStatus, String location) {
        this.id = id;
        this.creator = creator;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.placeStatus = placeStatus;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", creator='" + creator + '\'' +
                ", title='" + title + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", description='" + description + '\'' +
                ", placeStatus='" + placeStatus + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    public enum Status {
        CONCRETE,
        VOTING,
        SUGGESTED;
    }

    static class Loc {
        private String name;
        private int lat;
        private int lon;

        @Override
        public String toString() {
            return "Loc{" +
                    "name='" + name + '\'' +
                    ", lat=" + lat +
                    ", lon=" + lon +
                    '}';
        }
    }

    public static class Create {
        private String title;
        private String startTime;
        private String endTime;
        private String description;
        private String placeStatus;
        private String location;

        public Create(String title, ZonedDateTime startTime, ZonedDateTime endTime, String description, Status placeStatus, String location) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault());
            this.title = title;
            this.startTime = startTime.format(formatter);
            Log.e("Debugger", this.startTime);
            this.endTime = endTime.format(formatter);
            this.description = description;
            this.placeStatus = placeStatus.toString();
            this.location = location;
        }

        @Override
        public String toString() {
            return "Create{" +
                    "title='" + title + '\'' +
                    ", startTime='" + startTime + '\'' +
                    ", endTime='" + endTime + '\'' +
                    ", description='" + description + '\'' +
                    ", placeStatus='" + placeStatus + '\'' +
                    ", location='" + location + '\'' +
                    '}';
        }
    }

    public static class GET {
        private String id;
        private String creator;
        private String title;
        private int[] startTime;
        private int[] endTime;
        private String description;
        private String placeStatus;
        private String location;

        public EventModel getEvent() {
            return new EventModel(id,
                    creator,
                    title,
                    DateTime.intToDateTime(startTime),
                    DateTime.intToDateTime(endTime),
                    description,
                    Status.valueOf(placeStatus),
                    location);
        }
    }
}
