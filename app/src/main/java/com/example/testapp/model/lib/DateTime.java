package com.example.testapp.model.lib;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTime {
    public static LocalDateTime intToDateTime(int[] dateTime) {
        if (dateTime.length >= 5) {
            return LocalDateTime.of(dateTime[0],
                    dateTime[1], dateTime[2], dateTime[3], dateTime[4]);
        } else {
            throw new IllegalArgumentException("Datetime array needs length 5");
        }
    }

    public static int[] intToDateTime(LocalDateTime dateTime) {
        return new int[]{dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond(), dateTime.getNano()};
    }

    public static LocalDate stringToDate(String year, String month, String day) {
        return LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }
}
