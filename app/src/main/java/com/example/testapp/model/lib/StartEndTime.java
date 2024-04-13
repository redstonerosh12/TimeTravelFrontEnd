package com.example.testapp.model.lib;

import androidx.annotation.NonNull;

import java.time.LocalTime;
import java.util.Locale;

public class StartEndTime {
    private final LocalTime startTime;
    private final LocalTime endTime;

    public StartEndTime(String timePeriod) {
        String[] strTime = timePeriod.split("-");
        this.startTime = convertStringTime(strTime[0]);
        this.endTime = convertStringTime(strTime[1]);
    }

    public StartEndTime(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    private LocalTime convertStringTime(String time) {
        return LocalTime.of(Integer.parseInt(time.substring(0, 2)), Integer.parseInt(time.substring(2, 4)));
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%02d%02d-%02d%02d", startTime.getHour(), startTime.getMinute(), endTime.getHour(), endTime.getMinute());
    }
}
