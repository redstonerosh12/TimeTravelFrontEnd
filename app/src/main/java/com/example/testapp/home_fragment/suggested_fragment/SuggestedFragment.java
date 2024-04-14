package com.example.testapp.home_fragment.suggested_fragment;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.R;
import com.example.testapp.home_fragment.lib.CommonFragment;
import com.example.testapp.model.EventModel;

import java.util.ArrayList;

public class SuggestedFragment extends CommonFragment {
    public SuggestedFragment() {
        super(R.layout.suggestion_fragment);
    }

    @Override
    protected EventModel.Status setStatus() {
        return EventModel.Status.SUGGESTED;
    }

    @Override
    protected RecyclerView.Adapter<?> setAdapter(ArrayList<EventModel> events) {
        return new RecyclerSuggestedEventAdapter(events, SuggestedFragment.this);
    }
}