package com.example.testapp.home_fragment.suggested_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testapp.CommonDateSelected;
import com.example.testapp.MainActivity;
import com.example.testapp.R;
import com.example.testapp.api.API;
import com.example.testapp.api.DataSource;
import com.example.testapp.home_fragment.CreateEventActivity;
import com.example.testapp.home_fragment.RecyclerViewInterface;
import com.example.testapp.model.EventModel;

import java.time.LocalDate;
import java.util.ArrayList;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SuggestedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuggestedFragment extends Fragment implements RecyclerViewInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static ArrayList<SuggestedEventModel> suggestedEventModelList = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SuggestedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SuggestedFragment newInstance(String param1, String param2) {
        SuggestedFragment fragment = new SuggestedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.suggestion_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.SuggestedRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        AppCompatButton createEventButton = view.findViewById(R.id.goToCreateEventActivityButton);
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment suggestedFragment = SuggestedFragment.this;
                Context context = suggestedFragment.requireContext();
                Intent createEventIntent = new Intent(context, CreateEventActivity.class);
                suggestedFragment.startActivity(createEventIntent);
            }
        });

        TextView dateSelected = view.findViewById(R.id.dateSelected);
        TextView yearSelected = view.findViewById(R.id.textView6); //this is constraintLayout playing its tricks
        CommonDateSelected commonDateSelection = MainActivity.getCommonDateSelected();

        String travelPlanId = "1";
        LocalDate date = commonDateSelection.selectedDate.toLocalDate();

        String dateSelectedText = commonDateSelection.getDMMM();
        dateSelected.setText(dateSelectedText);
        yearSelected.setText(commonDateSelection.getYYYY());

        AppCompatButton prevDayButton = view.findViewById(R.id.previousDayButton);
        AppCompatButton nextDayButton = view.findViewById(R.id.nextDayButton);

        prevDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonDateSelection.prevDay();
                dateSelected.setText(commonDateSelection.getDMMM());
                yearSelected.setText(commonDateSelection.getYYYY());
                Log.d("CommonDateSelected.button", commonDateSelection.getDMMM());
                updateRecycleView(recyclerView, travelPlanId, commonDateSelection.selectedDate.toLocalDate());
            }
        });

        nextDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonDateSelection.nextDay();
                dateSelected.setText(commonDateSelection.getDMMM());
                yearSelected.setText(commonDateSelection.getYYYY());
                Log.d("CommonDateSelected.button", commonDateSelection.getDMMM());
                updateRecycleView(recyclerView, travelPlanId, commonDateSelection.selectedDate.toLocalDate());
            }
        });

        updateRecycleView(recyclerView, travelPlanId, date);
    }

    public void updateRecycleView(RecyclerView recyclerView, String travelPlanId, LocalDate date) {
        DataSource.getEventsByDate(travelPlanId, date, EventModel.Status.SUGGESTED, new API.Callback<ArrayList<EventModel>>() {
            @Override
            public void onFailure(Response<ArrayList<EventModel>> response) {

            }

            @Override
            public void onResponse(ArrayList<EventModel> events) {
                recyclerView.setAdapter(new RecyclerSuggestedEventAdapter(requireContext(), events, SuggestedFragment.this));
            }
        });
    }

    @Override
    public void onItemClick(int positon) {

    }
}