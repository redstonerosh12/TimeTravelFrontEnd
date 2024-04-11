package com.example.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class IndividualDayActivity extends AppCompatActivity {
    String date;
    String year;
    CommonDateSelected commonDateSelection = MainActivity.getCommonDateSelected();
    TextView ddmm;
    TextView yyyy;
    AppCompatButton nextDayButton;
    AppCompatButton prevDayButton;
    TextView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_day);
        ddmm = findViewById(R.id.date_display);
        yyyy = findViewById(R.id.year_display);
        ddmm.setText(commonDateSelection.getDMMM());
        yyyy.setText(commonDateSelection.getYYYY());
        nextDayButton = findViewById(R.id.nextDayButton);
        prevDayButton = findViewById(R.id.previousDayButton);
        backButton = findViewById(R.id.back_button);
        prevDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonDateSelection.prevDay();
                ddmm.setText(commonDateSelection.getDMMM());
                yyyy.setText(commonDateSelection.getYYYY());
            }
        });
        nextDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonDateSelection.nextDay();
                ddmm.setText(commonDateSelection.getDMMM());
                yyyy.setText(commonDateSelection.getYYYY());
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndividualDayActivity.this, MainActivity.class);
                intent.putExtra("from_calendar", true);
                startActivity(intent);
            }
        });
    }
}
