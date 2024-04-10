package com.example.testapp.model;

import java.time.LocalDateTime;

public class DateTime {
    public static LocalDateTime intToDateTime(int[] dateTime) {
        if (dateTime.length == 7) {
            return LocalDateTime.of(dateTime[0],
                    dateTime[1], dateTime[2], dateTime[3], dateTime[4], dateTime[5], dateTime[6]);
        } else {
            throw new IllegalArgumentException("Datetime array needs length 7");
        }
    }
}
