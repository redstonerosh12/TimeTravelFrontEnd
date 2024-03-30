package com.example.testapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConcreteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConcreteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static ArrayList<EventModel> eventModelList = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConcreteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConcreteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConcreteFragment newInstance(String param1, String param2) {
        ConcreteFragment fragment = new ConcreteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public static void setUpEventModelList(){
        eventModelList.add( new EventModel("0600-0700", "drink tea", "wake up and drink tea"));
        eventModelList.add( new EventModel("0700-0800", "drink coffee", "wake up and drink coffee"));
        eventModelList.add( new EventModel("0800-0900", "eat breakfast", "go eat at the buffet"));
        eventModelList.add( new EventModel("0600-0700", "drink tea", "wake up and drink tea"));
        eventModelList.add( new EventModel("0700-0800", "drink coffee", "wake up and drink coffee"));
        eventModelList.add( new EventModel("0800-0900", "eat breakfast", "go eat at the buffet"));
        eventModelList.add( new EventModel("0600-0700", "drink tea", "wake up and drink tea"));
        eventModelList.add( new EventModel("0700-0800", "drink coffee", "wake up and drink coffee"));
        eventModelList.add( new EventModel("0800-0900", "eat breakfast", "go eat at the buffet"));
        eventModelList.add( new EventModel("0600-0700", "drink tea", "wake up and drink tea"));
        eventModelList.add( new EventModel("0700-0800", "drink coffee", "wake up and drink coffee"));
        eventModelList.add( new EventModel("0800-0900", "eat breakfast", "go eat at the buffet"));
        eventModelList.add( new EventModel("0600-0700", "drink tea", "wake up and drink tea"));
        eventModelList.add( new EventModel("0700-0800", "drink coffee", "wake up and drink coffee"));
        eventModelList.add( new EventModel("0800-0900", "eat breakfast", "go eat at the buffet"));




    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // go from concrete to home, and from there to main activity
//        Activity mainActivity = getActivity();
//        if(mainActivity == null){
//            mainActivity = getParentFragment().getActivity();
//        }
//        RecyclerView recyclerView =mainActivity.findViewById(R.id.ConcreteRecyclerView);
//        Log.d("Recycler", "I have gotten recyclerView");




//        RecyclerConcreteEventAdapter adapter = new RecyclerConcreteEventAdapter(requireContext(), eventModelList);
//        Log.d("Recycler", "Adapter in fragment has gotten recycler from mainActivity successfully");

//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager( new LinearLayoutManager(requireContext()));







    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.concrete_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        setUpEventModelList();

        RecyclerConcreteEventAdapter adapter = new RecyclerConcreteEventAdapter(requireContext(), eventModelList);
        Log.d("Recycler", "Adapter in fragment has gotten recycler from mainActivity successfully");

        RecyclerView recyclerView = view.findViewById(R.id.ConcreteRecyclerView);
        Log.d("Recycler", "I have gotten recyclerView");

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager( new LinearLayoutManager(requireContext()));



    }
}