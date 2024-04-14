package com.example.testapp.model;

import android.annotation.SuppressLint;

import com.example.testapp.api.API;
import com.example.testapp.model.lib.APIDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import okhttp3.ResponseBody;

public class TravelPlan {
    private final String title;
    private final int id;
    private int[] startDate;
    private int[] endDate;
    private String creator;
    private String joinCode;
    private ArrayList<EventModel> events;

    public TravelPlan(int id, String title, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.title = title;
        this.startDate = parseDate(startDate);
        this.endDate = parseDate(endDate);
    }

    public TravelPlan(int id, String title, LocalDate startDate, LocalDate endDate, String creator, String joinCode, ArrayList<EventModel> events) {
        this(id, title, startDate, endDate);
        this.creator = creator;
        this.joinCode = joinCode;
        this.events = events;
    }

    public static LocalDate intDateConvertor(int[] date) {
        return LocalDate.of(date[0], date[1], date[2]);
    }

    public static int[] parseDate(LocalDate date) {
        int[] d = new int[3];
        d[0] = date.getYear();
        d[1] = date.getMonthValue();
        d[2] = date.getDayOfMonth();
        return d;
    }

    public static API.APIBuilder<TravelPlan> create(String title, LocalDate startDate, LocalDate endDate) {
        return API.TravelPlans.create(new Create(title, startDate, endDate));
    }

    public static API.APIBuilder<TravelPlan> join(String joinCode) {
        return API.TravelPlans.joinTravelPlan(joinCode);
    }

    public ArrayList<EventModel> getEvents() {
        return events;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getStartDate() {
        return intDateConvertor(startDate);
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = parseDate(startDate);
    }

    public LocalDate getEndDate() {
        return intDateConvertor(endDate);
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = parseDate(endDate);
    }

    public String getCreator() {
        return creator;
    }

    public String getJoinCode() {
        return joinCode;
    }

    public String getId() {
        return String.valueOf(id);
    }

    public API.APIBuilder<ResponseBody> update() {
        return API.TravelPlans.update(this);
    }

    @Override
    public String toString() {
        return "TravelPlan{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate=" + intDateConvertor(startDate).toString() +
                ", endDate=" + intDateConvertor(endDate).toString() +
                ", creator='" + creator + '\'' +
                ", joinCode='" + joinCode + '\'' +
                ", events=" + events +
                '}';
    }

    public static class Create {
        private final String title;
        private final String startDate;
        private final String endDate;

        public Create(TravelPlan travelPlan) {
            this(travelPlan.title, intDateConvertor(travelPlan.startDate), intDateConvertor(travelPlan.endDate));
        }

        @SuppressLint("DefaultLocale")
        public Create(String title, LocalDate startDate, LocalDate endDate) {
            this.title = title;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.startDate = startDate.format(formatter);
            this.endDate = endDate.format(formatter);
        }

        public TravelPlan toTravelPlan(int id, String creator, String joinCode) {
            LocalDate startDate = APIDate.formatString(this.startDate);
            LocalDate endDate = APIDate.formatString(this.endDate);
            return new TravelPlan(id, title, startDate, endDate, creator, joinCode, new ArrayList<>());
        }
    }
}
