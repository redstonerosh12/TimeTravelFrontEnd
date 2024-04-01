package com.example.testapp.home_fragment.suggested_fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testapp.R;
import com.example.testapp.home_fragment.RecyclerViewInterface;

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
        suggestedEventModelList.add( new SuggestedEventModel("0600-0700", "drink tea", "wake up and drink tea", "1", true));
        suggestedEventModelList.add( new SuggestedEventModel("0700-0800", "drink coffee", "wake up and drink coffee", "2", true));
        suggestedEventModelList.add( new SuggestedEventModel("0800-0900", "eat breakfast", "go eat at the buffet", "3", false));
        suggestedEventModelList.add( new SuggestedEventModel("0600-0700", "drink tea", "wake up and drink tea", "4", false));
        suggestedEventModelList.add( new SuggestedEventModel("0700-0800", "drink coffee", "wake up and drink coffee", "5", false));
        suggestedEventModelList.add( new SuggestedEventModel("0800-0900", "eat breakfast", "go eat at the buffet", "6",false));
        suggestedEventModelList.add( new SuggestedEventModel("0600-0700", "drink tea", "wake up and drink tea", "7",false));
        suggestedEventModelList.add( new SuggestedEventModel("0700-0800", "drink coffee", "wake up and drink coffee", "8",false));
        suggestedEventModelList.add( new SuggestedEventModel("0800-0900", "eat breakfast", "go eat at the buffet", "9",false));
        suggestedEventModelList.add( new SuggestedEventModel("0600-0700", "drink tea", "wake up and drink tea", "10",false));
        suggestedEventModelList.add( new SuggestedEventModel("0700-0800", "drink coffee", "wake up and drink coffee", "11",false));
        suggestedEventModelList.add( new SuggestedEventModel("0800-0900", "eat breakfast", "go eat at the buffet", "12",false));
        suggestedEventModelList.add( new SuggestedEventModel("0600-0700", "drink tea", "wake up and drink tea", "13",true));
        suggestedEventModelList.add( new SuggestedEventModel("0700-0800", "drink coffee", "wake up and drink coffee", "14",true));
        suggestedEventModelList.add( new SuggestedEventModel("0800-0900", "eat breakfast", "go eat at the buffet", "14",false));




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