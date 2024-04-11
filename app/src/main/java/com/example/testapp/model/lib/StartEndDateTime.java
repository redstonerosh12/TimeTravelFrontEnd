package com.example.testapp.model.lib;

import java.time.LocalDateTime;

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

    @Override
    public String toString() {
        return "StartEndDateTime{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
