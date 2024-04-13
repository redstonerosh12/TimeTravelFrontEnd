package com.example.testapp.model.lib;

import android.util.Log;

import com.example.testapp.model.EventModel;

import java.time.LocalTime;
import java.util.ArrayList;

public class DateTimeAVL extends AVLTree {
    private int number_of_datetime = 0;

    public DateTimeAVL(ArrayList<EventModel> dateTimeArrayList) {
        for (StartEndDateTime set : dateTimeArrayList) {
            insert(set.getStartTime().toLocalTime(), set.getEndTime().toLocalTime());
            number_of_datetime++;
        }
    }

    public int getNumber_of_datetime() {
        return number_of_datetime;
    }

    void insert(LocalTime startTime, LocalTime endTime) {
        root = insert(root, startTime, endTime);
    }

    public boolean checkConflict(StartEndDateTime set) {
        return checkConflict(set.getStartTime().toLocalTime(), set.getEndTime().toLocalTime());
    }

    public boolean checkConflict(StartEndTime set) {
        return checkConflict(set.getStartTime(), set.getEndTime());
    }
}
