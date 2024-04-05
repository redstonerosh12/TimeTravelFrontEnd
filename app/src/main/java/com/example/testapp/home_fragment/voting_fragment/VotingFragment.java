package com.example.testapp.home_fragment.voting_fragment;

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

import com.example.testapp.R;
import com.example.testapp.home_fragment.CreateEventActivity;
import com.example.testapp.home_fragment.RecyclerViewInterface;
import com.example.testapp.home_fragment.concrete_fragment.ConcreteFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VotingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VotingFragment extends Fragment implements RecyclerViewInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static ArrayList<VotingEventModel> votingEventModelList = new ArrayList<>();

    public VotingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VotingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VotingFragment newInstance(String param1, String param2) {
        VotingFragment fragment = new VotingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static void setUpEventModelList(){
        votingEventModelList.clear();
        votingEventModelList.add( new VotingEventModel("0600-0700", "drink tea", "wake up and drink tea", "1", true, true));
        votingEventModelList.add( new VotingEventModel("0700-0800", "drink coffee", "wake up and drink coffee", "2", true, false));
        votingEventModelList.add( new VotingEventModel("0800-0900", "eat breakfast", "go eat at the buffet", "3", false, false));
        votingEventModelList.add( new VotingEventModel("0600-0700", "drink tea", "wake up and drink tea", "4", false, false));
        votingEventModelList.add( new VotingEventModel("0700-0800", "drink coffee", "wake up and drink coffee", "5", false, false));
        votingEventModelList.add( new VotingEventModel("0800-0900", "eat breakfast", "go eat at the buffet", "6",false, false));
        votingEventModelList.add( new VotingEventModel("0600-0700", "drink tea", "wake up and drink tea", "7",false, false));
        votingEventModelList.add( new VotingEventModel("0700-0800", "drink coffee", "wake up and drink coffee", "8",false, false));
        votingEventModelList.add( new VotingEventModel("0800-0900", "eat breakfast", "go eat at the buffet", "9",false, false));
        votingEventModelList.add( new VotingEventModel("0600-0700", "drink tea", "wake up and drink tea", "10",false, false));
        votingEventModelList.add( new VotingEventModel("0700-0800", "drink coffee", "wake up and drink coffee", "11",false, false));
        votingEventModelList.add( new VotingEventModel("0800-0900", "eat breakfast", "go eat at the buffet", "12",false, false));
        votingEventModelList.add( new VotingEventModel("0600-0700", "drink tea", "wake up and drink tea", "13",true, true));
        votingEventModelList.add( new VotingEventModel("0700-0800", "drink coffee", "wake up and drink coffee", "14",true, true));
        votingEventModelList.add( new VotingEventModel("0800-0900", "eat breakfast", "go eat at the buffet", "14",false, true));




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
        return inflater.inflate(R.layout.voting_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        AppCompatButton createEventButton = view.findViewById(R.id.goToCreateEventActivityButton);
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment votingFragment = VotingFragment.this;
                Context context = votingFragment.requireContext();
                Intent createEventIntent = new Intent(context, CreateEventActivity.class);
                votingFragment.startActivity(createEventIntent);
            }
        });

        setUpEventModelList();

        RecyclerVotingEventAdapter adapter = new RecyclerVotingEventAdapter(requireContext(), votingEventModelList, this);
        Log.d("Recycler", "Adapter in voting fragment has gotten recycler from mainActivity successfully");

        RecyclerView recyclerView = view.findViewById(R.id.VotingRecyclerView);
        Log.d("Recycler", "I have gotten voting recyclerView");

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager( new LinearLayoutManager(requireContext()));



    }

    @Override
    public void onItemClick(int positon) {

    }
}