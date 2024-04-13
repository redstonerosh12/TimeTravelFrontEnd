package com.example.testapp.model.lib;

import androidx.annotation.NonNull;

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

    @NonNull
    @Override
    public String toString() {
        return traverseNode(root);
    }

    public String traverseNode(AVLNode node) {
        String info = (new StartEndTime(node.startTime, node.endTime)).toString();
        if (node.right != null || node.left != null) {
            if (node.left != null) {
                info += " left > " + traverseNode(node.left);
            }
            if (node.right != null) {
                info += " right > " + traverseNode(node.right);
            }
        } else {
            info += "(end)";
        }
        return info;
    }
}
