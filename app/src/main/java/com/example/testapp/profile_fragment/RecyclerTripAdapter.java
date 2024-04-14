package com.example.testapp.profile_fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.R;
import com.example.testapp.lib.ConfigurationManager;
import com.example.testapp.model.TravelPlan;

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
        if ((config.getId() == null && position == 0) || id.equals(String.valueOf(config.getId()))) {
            config.setId(id);
            holder.selected();
            selectedHolder = holder;
        }
        holder.selectButton.setOnClickListener(v -> {
            config.setId(id);
            holder.selected();
            if (selectedHolder != null) selectedHolder.deselect();
            selectedHolder = holder;
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder {
        private final TextView countryTextView;
        private final Button selectButton;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            countryTextView = itemView.findViewById(R.id.tripCountry);
            selectButton = itemView.findViewById(R.id.selectButton);
        }

        public void selected() {
            selectButton.setText(R.string.selected);
            selectButton.setEnabled(false);
        }

        public void deselect() {
            selectButton.setText(R.string.select);
            selectButton.setEnabled(true);
        }
    }
}

