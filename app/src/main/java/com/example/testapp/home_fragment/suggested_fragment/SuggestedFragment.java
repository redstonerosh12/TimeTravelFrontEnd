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
import com.example.testapp.home_fragment.CreateEventActivity;
import com.example.testapp.home_fragment.RecyclerViewInterface;
import com.example.testapp.home_fragment.concrete_fragment.ConcreteFragment;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private static ArrayList<SuggestedEventModel> suggestedEventModelList = new ArrayList<>();

    public SuggestedFragment() {
        // Required empty public constructor
    }

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


    public static void setUpEventModelList(){
        suggestedEventModelList.clear();

        ZonedDateTime six = ZonedDateTime.of(2024, 4, 1, 6, 0, 0, 0, ZoneId.systemDefault()); // Year, month, day, hour, minute, second, nanosecond, ZoneId
        ZonedDateTime seven = ZonedDateTime.of(2024, 4, 1, 7, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime eight = ZonedDateTime.of(2024, 4, 1, 8, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime nine = ZonedDateTime.of(2024, 4, 1, 9, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime ten = ZonedDateTime.of(2024, 4, 1, 10, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime eleven = ZonedDateTime.of(2024, 4, 1, 11, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime twelve = ZonedDateTime.of(2024, 4, 1, 12, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime thirteen = ZonedDateTime.of(2024, 4, 1, 13, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime fourteen = ZonedDateTime.of(2024, 4, 1, 14, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime fifteen = ZonedDateTime.of(2024, 4, 1, 15, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime sixteen = ZonedDateTime.of(2024, 4, 1, 16, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime seventeen = ZonedDateTime.of(2024, 4, 1, 17, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime eighteen = ZonedDateTime.of(2024, 4, 1, 18, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime nineteen = ZonedDateTime.of(2024, 4, 1, 19, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime twenty = ZonedDateTime.of(2024, 4, 1, 20, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime twentyOne = ZonedDateTime.of(2024, 4, 1, 21, 0, 0, 0, ZoneId.systemDefault());

        suggestedEventModelList.add( new SuggestedEventModel(six, seven, "drink tea", "wake up and drink tea", "1", true));
        suggestedEventModelList.add( new SuggestedEventModel(seven, eight, "drink coffee", "wake up and drink coffee", "2", true));
        suggestedEventModelList.add( new SuggestedEventModel(eight, nine, "eat breakfast", "go eat at the buffet", "3", false));
        suggestedEventModelList.add( new SuggestedEventModel(nine, ten, "drink tea", "wake up and drink tea", "4", false));
        suggestedEventModelList.add( new SuggestedEventModel(ten, eleven, "drink coffee", "wake up and drink coffee", "5", false));
        suggestedEventModelList.add( new SuggestedEventModel(eleven, twelve, "eat breakfast", "go eat at the buffet", "6",false));
        suggestedEventModelList.add( new SuggestedEventModel(twelve, thirteen, "drink tea", "wake up and drink tea", "7",false));
        suggestedEventModelList.add( new SuggestedEventModel(thirteen, fourteen, "drink coffee", "wake up and drink coffee", "8",false));
        suggestedEventModelList.add( new SuggestedEventModel(fourteen, fifteen, "eat breakfast", "go eat at the buffet", "9",false));
        suggestedEventModelList.add( new SuggestedEventModel(fifteen, sixteen, "drink tea", "wake up and drink tea", "10",false));
        suggestedEventModelList.add( new SuggestedEventModel(sixteen, seventeen, "drink coffee", "wake up and drink coffee", "11",false));
        suggestedEventModelList.add( new SuggestedEventModel(seventeen, eighteen, "eat breakfast", "go eat at the buffet", "12",false));
        suggestedEventModelList.add( new SuggestedEventModel(eighteen, nineteen, "drink tea", "wake up and drink tea", "13",true));
        suggestedEventModelList.add( new SuggestedEventModel(nineteen, twenty, "drink coffee", "wake up and drink coffee", "14",true));
        suggestedEventModelList.add( new SuggestedEventModel(twenty, twentyOne, "eat breakfast", "go eat at the buffet", "14",false));




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
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

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
            }
        });

        nextDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonDateSelection.nextDay();
                dateSelected.setText(commonDateSelection.getDMMM());
                yearSelected.setText(commonDateSelection.getYYYY());
                Log.d("CommonDateSelected.button", commonDateSelection.getDMMM());
            }
        });

        setUpEventModelList();

        RecyclerSuggestedEventAdapter adapter = new RecyclerSuggestedEventAdapter(requireContext(), suggestedEventModelList, this);
        Log.d("Recycler", "Adapter in suggested fragment has gotten recycler from mainActivity successfully");

        RecyclerView recyclerView = view.findViewById(R.id.SuggestedRecyclerView);
        Log.d("Recycler", "I have gotten suggested recyclerView");

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager( new LinearLayoutManager(requireContext()));



    }

    @Override
    public void onItemClick(int positon) {

    }
}