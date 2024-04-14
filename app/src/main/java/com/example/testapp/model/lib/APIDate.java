package com.example.testapp.model.lib;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class APIDate {
    private static final String FORMAT = "yyyy-MM-dd";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT);

    public static String formatDate(LocalDate date) {
        return date.format(formatter);
    }

    public static LocalDate formatString(String date) {
        return LocalDate.parse(date, formatter);
    }
}
