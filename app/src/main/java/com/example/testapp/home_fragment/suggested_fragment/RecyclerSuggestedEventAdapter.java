package com.example.testapp.home_fragment.suggested_fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.R;
import com.example.testapp.home_fragment.RecyclerViewInterface;
import com.example.testapp.model.EventModel;

import java.util.ArrayList;
import java.util.Locale;


public class RecyclerSuggestedEventAdapter extends RecyclerView.Adapter<RecyclerSuggestedEventAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    ArrayList<EventModel.GET> eventModelList;

    public RecyclerSuggestedEventAdapter(ArrayList<EventModel.GET> eventModelList,
                                         RecyclerViewInterface recyclerViewInterface) {
        this.eventModelList = eventModelList;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(parent.getContext());
        View view = inflator.inflate(R.layout.recycle_suggested_event_view_one_row, parent, false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerSuggestedEventAdapter.MyViewHolder holder, int position) {
        EventModel event = eventModelList.get(position).getEvent();
        holder.eventTimeAndTitle.setText(String.format(Locale.getDefault(), "%s %s", event.getTime(), event.getTitle()));
        holder.eventDescription.setText(event.getDescription());
        holder.dropdownIndicator.setImageResource(R.drawable.ui_element_dropdown_button_collapsed);
    }

    @Override
    public int getItemCount() {
        Log.d("Recycler.adapter", "item count is " + eventModelList.size());
        return eventModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView dropdownIndicator;
        TextView eventTimeAndTitle, eventDescription;
        Button pushToVotingButton, deleteEventButton;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            dropdownIndicator = itemView.findViewById(R.id.toggle_dropdown);
            eventTimeAndTitle = itemView.findViewById(R.id.time_and_event_name);
            eventDescription = itemView.findViewById(R.id.event_description);
            pushToVotingButton = itemView.findViewById(R.id.pushToVotingButton);
            deleteEventButton = itemView.findViewById(R.id.deleteEventButton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            if (eventDescription.getVisibility() == View.GONE) {
                                eventDescription.setVisibility(View.VISIBLE);
                                dropdownIndicator.setImageResource(R.drawable.ui_element_dropdown_button_expanded);
//                                if (eventModelList.get(position).getCreator().equals(Auth.getInstance().getUsername())) {
                                    deleteEventButton.setVisibility(View.VISIBLE);
                                    pushToVotingButton.setVisibility(View.VISIBLE);
//                                }
                            } else {
                                eventDescription.setVisibility(View.GONE);
                                dropdownIndicator.setImageResource(R.drawable.ui_element_dropdown_button_collapsed);
                                deleteEventButton.setVisibility(View.GONE);
                                pushToVotingButton.setVisibility(View.GONE);
                            }
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });


        }

    }
}
