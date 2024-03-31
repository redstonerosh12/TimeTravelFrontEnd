package com.example.testapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class RecyclerSuggestedEventAdapter extends RecyclerView.Adapter<RecyclerSuggestedEventAdapter.MyViewHolder>{
    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<SuggestedEventModel> eventModelList;

    public RecyclerSuggestedEventAdapter(Context context, ArrayList<SuggestedEventModel> eventModelList,
                                        RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.eventModelList = eventModelList;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public RecyclerSuggestedEventAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(context);


        View view = inflator.inflate(R.layout.recycle_suggested_event_view_one_row, parent, false);

        return new RecyclerSuggestedEventAdapter.MyViewHolder(view, recyclerViewInterface, eventModelList);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerSuggestedEventAdapter.MyViewHolder holder, int position) {

        holder.eventTimeAndTitle.setText(eventModelList.get(position).eventTime + " " +
                eventModelList.get(position).eventHeader);

        holder.eventDescription.setText(eventModelList.get(position).eventDescription);

        holder.dropdownIndicator.setImageResource(R.drawable.ui_element_dropdown_button_collapsed);



    }

    @Override
    public int getItemCount() {
        Log.d("Recycler.adapter", "item count is " + eventModelList.size());
        return eventModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView dropdownIndicator;
        TextView eventTimeAndTitle, eventDescription;
        Button pushToVotingButton, deleteEventButton;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface, ArrayList<SuggestedEventModel> eventModelList) {
            super(itemView);

            dropdownIndicator = itemView.findViewById(R.id.toggle_dropdown);
            eventTimeAndTitle = itemView.findViewById(R.id.time_and_event_name);
            eventDescription = itemView.findViewById(R.id.event_description);
            pushToVotingButton = itemView.findViewById(R.id.pushToVotingButton);
            deleteEventButton = itemView.findViewById(R.id.deleteEventButton);


            itemView.setOnClickListener( new View.OnClickListener(){
                @Override
                public void onClick(View view){

                    if(recyclerViewInterface != null){
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){



                            if(eventDescription.getVisibility() == View.GONE){
                                eventDescription.setVisibility(View.VISIBLE);
                                dropdownIndicator.setImageResource(R.drawable.ui_element_dropdown_button_expanded);
                                if(eventModelList.get(position).getEventOwnedByUser()){ //if user owns event
                                    deleteEventButton.setVisibility(View.VISIBLE);
                                    pushToVotingButton.setVisibility(View.VISIBLE);
                                }

                            }
                            else{
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
