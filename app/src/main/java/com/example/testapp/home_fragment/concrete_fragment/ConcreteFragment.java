package com.example.testapp.home_fragment.concrete_fragment;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.R;
import com.example.testapp.home_fragment.lib.CommonFragment;
import com.example.testapp.model.EventModel;

import java.util.ArrayList;

public class ConcreteFragment extends CommonFragment {
    public ConcreteFragment() {
        super(R.layout.concrete_fragment);
    }

    @Override
    protected EventModel.Status setStatus() {
        return EventModel.Status.CONCRETE;
    }

    @Override
    protected RecyclerView.Adapter<?> setAdapter(ArrayList<EventModel.GET> events) {
        return new RecyclerConcreteEventAdapter(events);
    }
}