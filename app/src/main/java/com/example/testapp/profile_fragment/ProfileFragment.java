package com.example.testapp.profile_fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testapp.R;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //    change to api call
    public static HashMap<String, String> profileData = new HashMap<String, String>();
    static {
        profileData.put("name", "Chani Kynes");
        profileData.put("email", "ChaniK@youtwitface.ar");
        // profileData.put("number", "+65 98765432");
        // profileData.put("address", "No 15 You street, Sietch Tabr road, Twit City, Face state");
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inf = inflater.inflate(R.layout.profile_fragment, container, false);
        TextView name = (TextView) inf.findViewById(R.id.profileName);
        name.setText(profileData.get("name"));
        TextView email = (TextView) inf.findViewById(R.id.profileEmail);
        email.setText(profileData.get("email"));
        /* 
        TextView number = (TextView) inf.findViewById(R.id.profileNumber);
        number.setText(profileData.get("number"));
        TextView address = (TextView) inf.findViewById(R.id.profileAddress);
        address.setText(profileData.get("address"));
         */
        return inf;
    }
}