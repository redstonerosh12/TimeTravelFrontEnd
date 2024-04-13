package com.example.testapp.model.lib;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class StartEndDateTime {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    protected StartEndDateTime() {

    }

    public StartEndDateTime(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public StartEndTime toTime() {
        return new StartEndTime(startTime.toLocalTime(), endTime.toLocalTime());
    }
    @Override
    public String toString() {
        return "StartEndDateTime{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
