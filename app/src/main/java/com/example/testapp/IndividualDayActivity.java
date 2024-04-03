package com.example.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IndividualDayActivity extends AppCompatActivity {
    String date;
    String year;
    TextView ddmm;
    TextView yyyy;
    TextView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_day);
        Intent intent = getIntent();
        date = String.valueOf(intent.getIntExtra("day", 0)) + "/" + String.valueOf(intent.getIntExtra("month", 0));
        year = String.valueOf(intent.getIntExtra("year", 0));
        ddmm = findViewById(R.id.date_display);
        yyyy = findViewById(R.id.year_display);
        ddmm.setText(date);
        yyyy.setText(year);
        backButton = findViewById(R.id.back_button);
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
