package com.example.testapp.model.lib;

import android.util.Log;

import com.example.testapp.model.EventModel;

import java.time.LocalTime;
import java.util.ArrayList;

public class DateTimeAVL extends AVLTree {
    public DateTimeAVL(ArrayList<EventModel.GET> dateTimeArrayList) {
        Log.e("DateTimeAVL", "Creating AVL");
        for (EventModel.GET get: dateTimeArrayList) {
            StartEndDateTime set = get.getEvent();
            Log.e("DateTimeAVL", set.toString());
            insert(set.getStartTime().toLocalTime(), set.getEndTime().toLocalTime());
        }
    }
    void insert(LocalTime startTime, LocalTime endTime) {
        root = insert(root, startTime, endTime);
    }
    public boolean checkConflict(StartEndDateTime set) {
        return checkConflict(set.getStartTime().toLocalTime(), set.getEndTime().toLocalTime());
    }
}
