package com.example.testapp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class EventModel {
    private String id;
    private String creator;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private Status placeStatus;
    private String location;

    public Status getPlaceStatus() {
        return placeStatus;
    }

    public EventModel(String id, String creator, String title, LocalDateTime startTime, LocalDateTime endTime, String description, Status placeStatus, String location) {
        this.id = id;
        this.creator = creator;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.placeStatus = placeStatus;
        this.location = location;
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
        return String.format(Locale.getDefault(), "%02d%02d-%02d%02d", startTime.getHour(), startTime.getMinute(), endTime.getHour(), endTime.getMinute());
    }

    public LocalDate getDate() {
        return startTime.toLocalDate();
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
