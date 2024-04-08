package com.example.testapp.model;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateTime {
    public static ZonedDateTime intToDateTime(int[] dateTime) {
        if (dateTime.length == 7) {
            return ZonedDateTime.of(dateTime[0],
                    dateTime[1], dateTime[2], dateTime[3], dateTime[4], dateTime[5], dateTime[6], ZoneId.systemDefault());
        } else {
            throw new IllegalArgumentException("Datetime array needs length 7");
        }
    }
}
