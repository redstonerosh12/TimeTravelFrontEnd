package com.example.testapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.testapp.model.EventModel;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class RecyclerIndividualDayAdapter extends RecyclerView.Adapter<RecyclerIndividualDayAdapter.MyViewHolder>{
    ArrayList<EventModel.GET> eventModelList;
    public RecyclerIndividualDayAdapter(ArrayList<EventModel.GET> eventModelList) {
        this.eventModelList = eventModelList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(parent.getContext());
        View view = inflator.inflate(R.layout.recycle_individual_day_event, parent, false);
        return new MyViewHolder(view);
    }
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        EventModel event = eventModelList.get(position).getEvent();
        long timeDifference = event.getStartTime().until(event.getEndTime(), ChronoUnit.HOURS);
        ViewGroup.LayoutParams params = holder.eventCard.getLayoutParams();
        params.height = 200 * (int) timeDifference;
        holder.eventDescription.setText(event.getDescription());
        holder.eventTimestamp.setText(event.getTime());
    }
    @Override
    public int getItemCount() {
        Log.d("Recycler.adapter", "item count is " + eventModelList.size());
        return eventModelList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView eventDescription, eventTimestamp;
        CardView eventCard;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            eventDescription = itemView.findViewById(R.id.event_display);
            eventTimestamp = itemView.findViewById(R.id.time_display);
            eventCard = itemView.findViewById(R.id.event_card);
        }
    }
}
