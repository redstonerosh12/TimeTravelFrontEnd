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

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    private RecyclerView tripRecyclerView;

    public ProfileFragment() {
        super(R.layout.profile_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView name = view.findViewById(R.id.profileName);
        name.setText(Auth.getInstance().getUsername());

        // Create Trip
        AppCompatButton createTripButton = view.findViewById(R.id.buttonCreateTravelPlan);
        createTripButton.setOnClickListener(v -> goTo(CreateTravelPlanActivity.class));

        // Join Trip
        AppCompatButton joinTripButton = view.findViewById(R.id.buttonJoinTravelPlan);
        joinTripButton.setOnClickListener(v -> goTo(JoinTravelPlanActivity.class));

        // Logout
        Button logoutButton = view.findViewById(R.id.logout);
        logoutButton.setOnClickListener(v -> {
            Auth.getInstance().logout(response -> {
                toast.makeLong("Successfully Logout");
                goTo(LoginPage.class);
            });
        });

        // Get Trips
        tripRecyclerView = view.findViewById(R.id.TravelPlanRecyclerView);
        tripRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tripRecyclerView.setAdapter(new RecyclerTravelPlanAdapter(new ArrayList<>()));

        TravelPlan.getTravelPlans()
                .setOnResponse(travelPlans -> {
                    Log.d(TAG, travelPlans.toString());
                    boolean exist = false;
                    for (TravelPlan travelPlan : travelPlans) {
                        if (travelPlan.getId().equals(config.getId())) {
                            exist = true;
                            break;
                        }
                    }
                    if (!exist) {
                        if (travelPlans.isEmpty()) config.setId(null);
                        else config.setId(travelPlans.get(0).getId());
                    }
                    tripRecyclerView.swapAdapter(new RecyclerTravelPlanAdapter(travelPlans), true);
                })
                .setOnFailure(res -> {

                }).fetch();
    }
}