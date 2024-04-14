package com.example.testapp.home_fragment.concrete_fragment;

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
import com.example.testapp.lib.ConfigurationManager;
import com.example.testapp.model.EventModel;

import java.util.ArrayList;
import java.util.Locale;

public class RecyclerConcreteEventAdapter extends RecyclerView.Adapter<RecyclerConcreteEventAdapter.MyViewHolder> {
    private static final ConfigurationManager config = ConfigurationManager.getInstance();
    ArrayList<EventModel.GET> eventModelList;

    public RecyclerConcreteEventAdapter(ArrayList<EventModel.GET> eventModelList) {
        this.eventModelList = eventModelList;
    }

    @NonNull
    @Override
    public RecyclerConcreteEventAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(parent.getContext());
        View view = inflator.inflate(R.layout.recycle_concrete_event_view_one_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerConcreteEventAdapter.MyViewHolder holder, int position) {
        holder.setEvent(eventModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return eventModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView dropdownIndicator;
        TextView eventTimeAndTitle, eventDescription;
        Button deleteEventButton;
//        private boolean isEventOwner = false;
//        private EventModel event = null;
        private String eventId;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dropdownIndicator = itemView.findViewById(R.id.toggle_dropdown);
            eventTimeAndTitle = itemView.findViewById(R.id.time_and_event_name);
            eventDescription = itemView.findViewById(R.id.event_description);
            deleteEventButton = itemView.findViewById(R.id.deleteEventButton);

            itemView.setOnClickListener(v -> {
                if (eventDescription.getVisibility() == View.GONE) {
                    eventDescription.setVisibility(View.VISIBLE);
                    dropdownIndicator.setImageResource(R.drawable.ui_element_dropdown_button_expanded);
                    //FIXME: Require isOwner Flag
//                  if(event == null) {
//                        API.Event.getEvent(config.getId(),eventId).setOnResponse(eventGet -> {
//                            event = eventGet.getEvent();
//                            Log.d("asd",event.toString());
////                            if(event.getCreator().equals(Auth.getInstance().getUsername())){
////                                deleteEventButton.setVisibility(View.VISIBLE);
////                                isEventOwner = true;
////                            }
//                        }).setOnFailure(res->{}).fetch();
//                    } else if(isEventOwner) deleteEventButton.setVisibility(View.VISIBLE);

                    deleteEventButton.setVisibility(View.VISIBLE);
                } else {
                    eventDescription.setVisibility(View.GONE);
                    dropdownIndicator.setImageResource(R.drawable.ui_element_dropdown_button_collapsed);
                    deleteEventButton.setVisibility(View.GONE);
                }
            });
        }

        public void setEvent(EventModel.GET eventGet) {
            EventModel event = eventGet.getEvent();
            eventTimeAndTitle.setText(String.format(Locale.getDefault(), "%s %s", event.getTime(), event.getTitle()));
            eventDescription.setText(event.getDescription());
            dropdownIndicator.setImageResource(R.drawable.ui_element_dropdown_button_collapsed);
            eventId = event.getId();
        }
    }
}
