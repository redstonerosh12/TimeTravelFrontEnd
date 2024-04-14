package com.example.testapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.api.API;
import com.example.testapp.api.DataSource;
import com.example.testapp.databinding.ActivityIndividualDayBinding;
import com.example.testapp.lib.AppCompatActivity;
import com.example.testapp.model.EventModel;

import java.util.ArrayList;

import retrofit2.Response;

public class IndividualDayActivity extends AppCompatActivity {
    private static final CommonDateSelected commonDateSelection = CommonDateSelected.getInstance();
    private RecyclerView recyclerView;
    private TextView dateSelected;
    private TextView yearSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityIndividualDayBinding binding = ActivityIndividualDayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.individualDayRecycler;
        recyclerView.setAdapter(new RecyclerIndividualDayAdapter(new ArrayList<>()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dateSelected = binding.dateDisplay;
        yearSelected = binding.yearDisplay;

        binding.backButton.setOnClickListener(v-> finish());

        AppCompatButton prevDayButton = binding.previousDayButton;
        prevDayButton.setOnClickListener(v -> {
            commonDateSelection.prevDay();
            updateDateSelector();
        });

        AppCompatButton nextDayButton = binding.nextDayButton;
        nextDayButton.setOnClickListener(v -> {
            commonDateSelection.nextDay();
            updateDateSelector();
        });

        updateDateSelector();
    }

    public void updateDateSelector() {
        dateSelected.setText(commonDateSelection.getDMMM());
        yearSelected.setText(commonDateSelection.getYYYY());
        Log.d(TAG, commonDateSelection.getDMMM());
        updateRecycleView();
    }

    public void updateRecycleView() {
        DataSource.getEventsByDate(config.getId(), commonDateSelection.selectedDate, EventModel.Status.CONCRETE, new API.Callback<ArrayList<EventModel.GET>>() {
            @Override
            public void onFailure(Response<ArrayList<EventModel.GET>> response) {

            }

            @Override
            public void onResponse(ArrayList<EventModel.GET> events) {
                recyclerView.swapAdapter(new RecyclerIndividualDayAdapter(events), false);
            }
        });
    }
}