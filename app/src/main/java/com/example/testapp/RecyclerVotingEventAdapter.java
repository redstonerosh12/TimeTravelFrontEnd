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



public class RecyclerVotingEventAdapter extends RecyclerView.Adapter<RecyclerVotingEventAdapter.MyViewHolder>{
    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<VotingEventModel> eventModelList;

    public RecyclerVotingEventAdapter(Context context, ArrayList<VotingEventModel> eventModelList,
                                         RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.eventModelList = eventModelList;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public RecyclerVotingEventAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(context);


        View view = inflator.inflate(R.layout.recycle_voting_event_view_one_row, parent, false);

        return new RecyclerVotingEventAdapter.MyViewHolder(view, recyclerViewInterface, eventModelList);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerVotingEventAdapter.MyViewHolder holder, int position) {
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

        ImageView dropdownIndicator, votingBarBase, votingBarNo; //votingBarBase - votingBarNo is votingBarYes
        TextView eventTimeAndTitle, eventDescription;
        Button yesButton, noButton;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface, ArrayList<VotingEventModel> eventModelList) {
            super(itemView);

            dropdownIndicator = itemView.findViewById(R.id.toggle_dropdown);
            eventTimeAndTitle = itemView.findViewById(R.id.time_and_event_name);
            eventDescription = itemView.findViewById(R.id.event_description);

            yesButton = itemView.findViewById(R.id.yesButton);
            noButton = itemView.findViewById(R.id.noButton);

            votingBarBase = itemView.findViewById(R.id.votingBarBase);
            votingBarNo = itemView.findViewById(R.id.votingBarNo);



            itemView.setOnClickListener( new View.OnClickListener(){
                @Override
                public void onClick(View view){

                    if(recyclerViewInterface != null){
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            Log.d("Recycler.Duplicate", "onClick of " + position);
                            Log.d("Recycler.Duplicate", "ID of red bar " + votingBarBase.getId());



                            if(eventDescription.getVisibility() == View.GONE){
                                Log.d("Recycler.Duplicate", "visibility setting of " + position);
                                eventDescription.setVisibility(View.VISIBLE);
                                dropdownIndicator.setImageResource(R.drawable.ui_element_dropdown_button_expanded);
                                if(eventModelList.get(position).getEventVotedOnByUser()){ //if user has voted event
                                    votingBarBase.setVisibility(View.VISIBLE);
                                    votingBarNo.setVisibility(View.VISIBLE);
                                }
                                else {
                                    noButton.setVisibility(View.VISIBLE);
                                    yesButton.setVisibility(View.VISIBLE);
                                }

                            }
                            else{
                                eventDescription.setVisibility(View.GONE);
                                dropdownIndicator.setImageResource(R.drawable.ui_element_dropdown_button_collapsed);
                                votingBarBase.setVisibility(View.GONE);
                                votingBarNo.setVisibility(View.GONE);
                                noButton.setVisibility(View.GONE);
                                yesButton.setVisibility(View.GONE);

                            }

                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });


        }

    }
}
