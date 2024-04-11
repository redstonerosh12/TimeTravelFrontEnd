package com.example.testapp.profile_fragment;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapp.LoginPage;
import com.example.testapp.R;
import com.example.testapp.home_fragment.CreateEventActivity;
import com.example.testapp.home_fragment.concrete_fragment.ConcreteEventModel;
import com.example.testapp.home_fragment.concrete_fragment.ConcreteFragment;
import com.example.testapp.home_fragment.concrete_fragment.RecyclerConcreteEventAdapter;
import com.example.testapp.middleware.Auth;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //    change to api call
    public static HashMap<String, String> profileData = new HashMap<String, String>();

    static {
        profileData.put("name", Auth.getInstance().getUsername());
//        profileData.put("email", "ChaniK@youtwitface.ar");
        // profileData.put("number", "+65 98765432");
        // profileData.put("address", "No 15 You street, Sietch Tabr road, Twit City, Face state");
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView tripRecyclerView;

//    private static ArrayList<ConcreteEventModel> tripModelList = new ArrayList<>();
    private RecyclerTripAdapter recyclerTripAdapter;
    private List<TripModel> tripList;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

//    public static void setUpTripModelList(){
//        tripModelList.clear();
//        tripModelList.add( new ConcreteEventModel(six, seven, "drink tea", "wake up and drink tea", "1", true));
//        tripModelList.add( new ConcreteEventModel(seven, eight, "drink coffee", "wake up and drink coffee", "2", true));
//        tripModelList.add( new ConcreteEventModel(eight, nine, "eat breakfast", "go eat at the buffet", "3", false));
//        tripModelList.add( new ConcreteEventModel(nine, ten, "drink tea", "wake up and drink tea", "4", false));
//        tripModelList.add( new ConcreteEventModel(ten, eleven, "drink coffee", "wake up and drink coffee", "5", false));
//        tripModelList.add( new ConcreteEventModel(eleven, twelve, "eat breakfast", "go eat at the buffet", "6",false));
//        tripModelList.add( new ConcreteEventModel(twelve, thirteen, "drink tea", "wake up and drink tea", "7",false));
//        tripModelList.add( new ConcreteEventModel(thirteen, fourteen, "drink coffee", "wake up and drink coffee", "8",false));
//        tripModelList.add( new ConcreteEventModel(fourteen, fifteen, "eat breakfast", "go eat at the buffet", "9",false));
//        tripModelList.add( new ConcreteEventModel(fifteen, sixteen, "drink tea", "wake up and drink tea", "10",false));
//        tripModelList.add( new ConcreteEventModel(sixteen, seventeen, "drink coffee", "wake up and drink coffee", "11",false));
//        tripModelList.add( new ConcreteEventModel(seventeen, eighteen, "eat breakfast", "go eat at the buffet", "12",false));
//        tripModelList.add( new ConcreteEventModel(eighteen, nineteen, "drink tea", "wake up and drink tea", "13",true));
//        tripModelList.add( new ConcreteEventModel(nineteen, twenty, "drink coffee", "wake up and drink coffee", "14",true));
//        tripModelList.add( new ConcreteEventModel(twenty, twentyOne, "eat breakfast", "go eat at the buffet", "15",false));
//  }

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
        View inf = inflater.inflate(R.layout.profile_fragment, container, false);
        TextView name = (TextView) inf.findViewById(R.id.profileName);
        name.setText(profileData.get("name"));
        /*
        TextView email = (TextView) inf.findViewById(R.id.profileEmail);
        email.setText(profileData.get("email"));
        TextView number = (TextView) inf.findViewById(R.id.profileNumber);
        number.setText(profileData.get("number"));
        TextView address = (TextView) inf.findViewById(R.id.profileAddress);
        address.setText(profileData.get("address"));
         */
        tripList = new ArrayList<>();
        tripRecyclerView = inf.findViewById(R.id.TripRecyclerView);
        tripRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerTripAdapter = new RecyclerTripAdapter(tripList);
        tripRecyclerView.setAdapter(recyclerTripAdapter);

        return inf;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppCompatButton createTripButton = view.findViewById(R.id.buttonCreateTrip);
        createTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment profileFragment = ProfileFragment.this;
                Context context = profileFragment.requireContext();
                Intent createTripIntent = new Intent(context, CreateTripActivity.class);
                profileFragment.startActivity(createTripIntent);
            }
        });

        AppCompatButton joinTripButton = view.findViewById(R.id.buttonJoinTrip);
        joinTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment profileFragment = ProfileFragment.this;
                Context context = profileFragment.requireContext();
                Intent joinTripIntent = new Intent(context, JoinTripActivity.class);
                profileFragment.startActivity(joinTripIntent);
            }
        });

        Button logoutButton = view.findViewById(R.id.logout);

        logoutButton.setOnClickListener(v -> {
            Auth.getInstance().logout(response -> {
                Toast.makeText(getActivity(), "Successfully Logout", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), LoginPage.class);
                startActivity(intent);
            });
        });


//        setUpTripModelList();
//
//        RecyclerConcreteTripAdapter adapter = new RecyclerConcreteEventAdapter(requireContext(), tripModelList, this);
//        Log.d("Recycler", "Adapter in fragment has gotten recycler from mainActivity successfully");
//
//        RecyclerView recyclerView = view.findViewById(R.id.ConcreteRecyclerView);
//        Log.d("Recycler", "I have gotten recyclerView");
//
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager( new LinearLayoutManager(requireContext()));

    }

    public void addTrip(TripModel newTrip) {
        tripList.add(newTrip);
        recyclerTripAdapter.notifyDataSetChanged();
    }

}