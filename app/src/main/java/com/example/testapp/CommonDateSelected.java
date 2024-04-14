package com.example.testapp;

import android.util.Log;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CommonDateSelected {
    public LocalDate selectedDate;
    private static CommonDateSelected instance;

    private CommonDateSelected(){
        selectedDate =  LocalDate.now();
    }

    public static CommonDateSelected getInstance(){
        if (instance == null){
            instance = new CommonDateSelected();
        }
        return instance;
    }
     public  String getDMMM() {
         int day = selectedDate.getDayOfMonth();
         DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMM");
         String month = (monthFormatter.format(selectedDate)).toUpperCase();

         return (day + " " + month);
     }

     public String getYYYY(){
         DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");
         return (yearFormatter.format(selectedDate));
     }

     public void nextDay(){
        selectedDate = selectedDate.plusDays(1);
     }
     public void prevDay(){
        selectedDate = selectedDate.plusDays(-1);
     }

     public void setSelectedDate(LocalDate selectedDate){
        this.selectedDate = selectedDate;
     }
     public LocalDate getSelectedDate(){
        return selectedDate;
    }
}
