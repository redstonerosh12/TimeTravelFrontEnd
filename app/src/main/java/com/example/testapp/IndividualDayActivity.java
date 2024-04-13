package com.example.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.api.API;
import com.example.testapp.api.DataSource;
import com.example.testapp.home_fragment.RecyclerViewInterface;
import com.example.testapp.model.EventModel;

import java.time.LocalDate;
import java.util.ArrayList;

import retrofit2.Response;

public class IndividualDayActivity extends AppCompatActivity implements RecyclerViewInterface {
    LocalDate date;
    String year;
    CommonDateSelected commonDateSelection = MainActivity.getCommonDateSelected();
    TextView ddmm;
    TextView yyyy;
    AppCompatButton nextDayButton;
    AppCompatButton prevDayButton;
    TextView backButton;
    String travelPlanId = "1";
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
        RecyclerView recyclerView = findViewById(R.id.individual_day_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        date = commonDateSelection.selectedDate.toLocalDate();
        prevDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonDateSelection.prevDay();
                ddmm.setText(commonDateSelection.getDMMM());
                yyyy.setText(commonDateSelection.getYYYY());
                Log.d("CommonDateSelected.button", commonDateSelection.getDMMM());
                updateRecycleView(recyclerView, travelPlanId, commonDateSelection.selectedDate.toLocalDate());
            }
        });
        nextDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonDateSelection.nextDay();
                ddmm.setText(commonDateSelection.getDMMM());
                yyyy.setText(commonDateSelection.getYYYY());
                Log.d("CommonDateSelected.button", commonDateSelection.getDMMM());
                updateRecycleView(recyclerView, travelPlanId, commonDateSelection.selectedDate.toLocalDate());
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
        updateRecycleView(recyclerView, travelPlanId, date);
    }
    public void updateRecycleView(RecyclerView recyclerView, String travelPlanId, LocalDate date) {
        DataSource.getEventsByDate(travelPlanId, date, EventModel.Status.CONCRETE, new API.Callback<ArrayList<EventModel>>() {
            @Override
            public void onFailure(Response<ArrayList<EventModel>> response) {
            }

            @Override
            public void onResponse(ArrayList<EventModel> events) {
                recyclerView.setAdapter(new RecyclerIndividualDayAdapter(recyclerView.getContext(), events, IndividualDayActivity.this));
                System.out.println(events);
            }
        });
    }
    @Override
    public void onItemClick(int position) {
    }
}