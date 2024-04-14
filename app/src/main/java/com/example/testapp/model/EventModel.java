package com.example.testapp.model;

import com.example.testapp.model.lib.DateTime;
import com.example.testapp.model.lib.StartEndDateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;

public class EventModel extends StartEndDateTime {
    private String id;
    private String creator;
    private String title;
    private String description;
    private Status placeStatus;
    private String location;

    public EventModel(String id, String creator, String title, LocalDateTime startTime, LocalDateTime endTime, String description, Status placeStatus, String location) {
        super();
        this.id = id;
        this.creator = creator;
        this.title = title;
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.description = description;
        this.placeStatus = placeStatus;
        this.location = location;
    }

    public Status getPlaceStatus() {
        return placeStatus;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getCreator() {
        return creator;
    }

    public String getTime() {
        LocalDateTime startTime = getStartTime();
        LocalDateTime endTime = getEndTime();
        return String.format(Locale.getDefault(), "%02d%02d-%02d%02d", startTime.getHour(), startTime.getMinute(), endTime.getHour(), endTime.getMinute());
    }

    public LocalDate getDate() {
        return getStartTime().toLocalDate();
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", creator='" + creator + '\'' +
                ", title='" + title + '\'' +
                ", startTime='" + getStartTime() + '\'' +
                ", endTime='" + getEndTime() + '\'' +
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

    public static class Create {
        private String title;
        private String startTime;
        private String endTime;
        private String description;
        private String placeStatus;
        private String location;

        public Create(String title, LocalDateTime startTime, LocalDateTime endTime, String description, Status placeStatus, String location) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault());
            this.title = title;
            this.startTime = ZonedDateTime.of(startTime, ZoneId.systemDefault()).format(formatter);
            this.endTime = ZonedDateTime.of(endTime, ZoneId.systemDefault()).format(formatter);
            this.description = description;
            this.placeStatus = placeStatus.toString();
            this.location = location;
        }

        public GET toGET(String creator) {
            int[] convertedStartTime = DateTime.intToDateTime(ZonedDateTime.parse(startTime).toLocalDateTime());
            int[] convertedEndTime = DateTime.intToDateTime(ZonedDateTime.parse(endTime).toLocalDateTime());
            return new GET("100", creator, title, convertedStartTime, convertedEndTime, description, placeStatus, location);
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

        public GET(String id, String creator, String title, int[] startTime, int[] endTime, String description, String placeStatus, String location) {
            this.id = id;
            this.creator = creator;
            this.title = title;
            this.startTime = startTime;
            this.endTime = endTime;
            this.description = description;
            this.placeStatus = placeStatus;
            this.location = location;
        }

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

    public static class SortbyTime implements Comparator<EventModel> {
        public int compare(EventModel a, EventModel b) {
            return a.getStartTime().compareTo(b.getStartTime());
        }
    }
}

