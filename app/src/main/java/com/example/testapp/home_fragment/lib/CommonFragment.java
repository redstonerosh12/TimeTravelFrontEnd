package com.example.testapp.home_fragment.lib;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.CommonDateSelected;
import com.example.testapp.R;
import com.example.testapp.RecyclerIndividualDayAdapter;
import com.example.testapp.api.API;
import com.example.testapp.api.DataSource;
import com.example.testapp.home_fragment.CreateEventActivity;
import com.example.testapp.home_fragment.RecyclerViewInterface;
import com.example.testapp.lib.Fragment;
import com.example.testapp.model.EventModel;

import java.util.ArrayList;

import retrofit2.Response;

public abstract class CommonFragment extends Fragment implements RecyclerViewInterface {
    private static final CommonDateSelected commonDateSelection = CommonDateSelected.getInstance();
    private RecyclerView recyclerView;
    private TextView dateSelected;
    private TextView yearSelected;
    private EventModel.Status status;

    public CommonFragment(int contentLayoutId) {
        super(contentLayoutId);
        status = setStatus();
    }

    protected abstract EventModel.Status setStatus();

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new RecyclerIndividualDayAdapter(new ArrayList<>()));

        AppCompatButton createEventButton = view.findViewById(R.id.goToCreateEventActivityButton);
        createEventButton.setOnClickListener(v -> goTo(CreateEventActivity.class));

        dateSelected = view.findViewById(R.id.dateSelected);
        yearSelected = view.findViewById(R.id.yearSelected);

        AppCompatButton prevDayButton = view.findViewById(R.id.previousDayButton);
        prevDayButton.setOnClickListener(v -> {
            commonDateSelection.prevDay();
            updateDateSelector();
        });

        AppCompatButton nextDayButton = view.findViewById(R.id.nextDayButton);
        nextDayButton.setOnClickListener(v -> {
            commonDateSelection.nextDay();
            updateDateSelector();
        });

        updateDateSelector();
    }

    public void updateDateSelector() {
        dateSelected.setText(commonDateSelection.getDMMM());
        yearSelected.setText(commonDateSelection.getYYYY());
        Log.d("CommonDateSelected.button", commonDateSelection.getDMMM());
        updateRecycleView();
    }

    public void updateRecycleView() {
        DataSource.getEventsByDate(config.getId(), commonDateSelection.selectedDate, status, new API.Callback<ArrayList<EventModel.GET>>() {
            @Override
            public void onFailure(Response<ArrayList<EventModel.GET>> response) {

            }

            @Override
            public void onResponse(ArrayList<EventModel.GET> events) {
                recyclerView.swapAdapter(setAdapter(events), false);
            }
        });
    }

    protected abstract RecyclerView.Adapter<?> setAdapter(ArrayList<EventModel.GET> events);

    @Override
    public void onItemClick(int position) {

    }
}