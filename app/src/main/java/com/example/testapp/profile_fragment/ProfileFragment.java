package com.example.testapp.profile_fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.testapp.LoginPage;
import com.example.testapp.R;
import com.example.testapp.lib.Fragment;
import com.example.testapp.middleware.Auth;
import com.example.testapp.model.TravelPlan;

public class ProfileFragment extends Fragment {
    private RecyclerView tripRecyclerView;
    private RecyclerTripAdapter recyclerTripAdapter;

    public ProfileFragment() {
        super(R.layout.profile_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView name = view.findViewById(R.id.profileName);
        name.setText(Auth.getInstance().getUsername());

        // Create Trip
        AppCompatButton createTripButton = view.findViewById(R.id.buttonCreateTrip);
        createTripButton.setOnClickListener(v -> goTo(CreateTripActivity.class));

        // Join Trip
        AppCompatButton joinTripButton = view.findViewById(R.id.buttonJoinTrip);
        joinTripButton.setOnClickListener(v -> goTo(JoinTripActivity.class));

        // Logout
        Button logoutButton = view.findViewById(R.id.logout);
        logoutButton.setOnClickListener(v -> {
            Auth.getInstance().logout(response -> {
                toast.makeLong("Successfully Logout");
                goTo(LoginPage.class);
            });
        });

        // Get Trips
        tripRecyclerView = view.findViewById(R.id.TripRecyclerView);
        tripRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        TravelPlan.getTravelPlans()
                .setOnResponse(travelPlans -> {
                    Log.d(TAG, travelPlans.toString());
                    recyclerTripAdapter = new RecyclerTripAdapter(travelPlans);
                    tripRecyclerView.setAdapter(recyclerTripAdapter);
                })
                .setOnFailure(res -> {

                }).fetch();
    }
}