package com.example.testapp.profile_fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.R;
import com.example.testapp.api.API;
import com.example.testapp.lib.ConfigurationManager;
import com.example.testapp.model.TravelPlan;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class RecyclerTripAdapter extends RecyclerView.Adapter<RecyclerTripAdapter.TripViewHolder> {
    private final List<TravelPlan> tripList;
    private final ConfigurationManager config = ConfigurationManager.getInstance();
    private TripViewHolder selectedHolder;

    public RecyclerTripAdapter(List<TravelPlan> tripList) {
        this.tripList = tripList;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_trip_view_one_row, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        TravelPlan trip = tripList.get(position);
        String id = trip.getId();
        holder.countryTextView.setText(trip.getTitle());
        View.OnClickListener click = v -> {
            API.TravelPlans.getTravelPlan(id)
                    .setOnResponse(travelPlanGet -> {
                        TravelPlan travelPlan = travelPlanGet.build();
                        config.setSelectedTravelPlan(travelPlan);
                        holder.selected(travelPlan.getJoinCode());
                    })
                    .setOnFailure(res -> {
                    }).fetch();
            config.setId(id);
            if (selectedHolder != null) selectedHolder.deselect();
            selectedHolder = holder;
        };

        if ((config.getId() == null && position == 0) || id.equals(String.valueOf(config.getId()))) {
            click.onClick(null);
        }
        holder.selectButton.setOnClickListener(click);
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder {
        private final TextView countryTextView;
        private final Button selectButton;
        private final MaterialButton joinCodeButton;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            countryTextView = itemView.findViewById(R.id.tripCountry);
            selectButton = itemView.findViewById(R.id.selectButton);
            joinCodeButton = itemView.findViewById(R.id.joinCodeButton);
            joinCodeButton.setVisibility(View.GONE);
        }

        public void selected(String joinCode) {
            joinCodeButton.setVisibility(View.VISIBLE);
            joinCodeButton.setOnClickListener(v-> {
                Context context = joinCodeButton.getContext();
                // https://developer.android.com/training/sharing/send#java
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, joinCode);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                context.startActivity(shareIntent);
//                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//                ClipData clip = ClipData.newPlainText("JoinCode", joinCode);
//                clipboard.setPrimaryClip(clip);
//                Toast.makeText(context,"Copied",Toast.LENGTH_SHORT).show();
            });
            selectButton.setText(R.string.selected);
            selectButton.setEnabled(false);
        }

        public void deselect() {
            joinCodeButton.setVisibility(View.GONE);
            selectButton.setText(R.string.select);
            selectButton.setEnabled(true);
        }
    }
}

