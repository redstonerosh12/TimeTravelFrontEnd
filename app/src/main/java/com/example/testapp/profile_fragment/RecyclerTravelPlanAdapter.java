package com.example.testapp.profile_fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.R;

import java.util.List;

public class RecyclerTravelPlanAdapter extends RecyclerView.Adapter<RecyclerTravelPlanAdapter.TravelPlanViewHolder> {
    private List<TravelPlanModel> travelPlanList;

    public RecyclerTravelPlanAdapter(List<TravelPlanModel> tripList) {
        this.travelPlanList = tripList;
    }

    @NonNull
    @Override
    public TravelPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_travel_plan_view_one_row, parent, false);
        return new TravelPlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelPlanViewHolder holder, int position) {
        TravelPlanModel trip = travelPlanList.get(position);
        holder.countryTextView.setText(trip.getCountry());
    }

    @Override
    public int getItemCount() {
        return travelPlanList.size();
    }

    public class TravelPlanViewHolder extends RecyclerView.ViewHolder {
        TextView countryTextView;

        public TravelPlanViewHolder(@NonNull View itemView) {
            super(itemView);
            countryTextView = itemView.findViewById(R.id.editTitle);
        }
    }
}

