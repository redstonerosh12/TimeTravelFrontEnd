package com.example.testapp.profile_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.testapp.R;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.testapp.calendar_fragment.CalendarFragment;
import com.example.testapp.home_fragment.HomeFragment;
import com.example.testapp.profile_fragment.ProfileFragment;
import com.google.android.material.navigation.NavigationBarView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateTripActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_trip_form);

        EditText editTitle = findViewById(R.id.editTitle);
        EditText editStartDate = findViewById(R.id.editStartDate);
        EditText editEndDate = findViewById(R.id.editEndDate);
        ArrayList<EditText> allFields = new ArrayList<>(Arrays.asList(editTitle, editStartDate, editEndDate));

//        AppCompatButton submitForm = findViewById(R.id.SubmitFormCreateTrip);

//        submitForm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int selectedTripTypeId = concreteOrSuggested.getCheckedRadioButtonId();
//
//                if (selectedTripTypeId == -1) {
//                    createToast("Trip type concrete or suggested must be selected!");
//                } else if (!isAllFieldsFilled(allFields)) {
//                    createToast("All fields must be filled in");
//                } else if (!isNumericallyFilled(editStartDate)) {
//                    createToast("Start date must be numerical!");
//                } else if (!isNumericallyFilled(editEndDate)) {
//                    createToast("End date must be numerical!");
//                } else if (!isValidLowerToUpperRange(editStartDate, editEndDate)) {
//                    createToast("Start date should be before the End date!");
//                }
//
//
//
//                else {
//                    Log.d("CreateTrip", "Passed all the form checks");
//                    //make the concrete trip model
//                    String tripDate = extractText(editStartDate) + " - " + extractText(editEndDate);
//                    String tripHeader = extractText(editTitle);
//
//                    Log.d("CreateTrip.NewTrip.Data", tripDate + tripHeader + tripDescription + tripID + tripOwnedByUser);
//
//                    Intent returnIntent = new Intent(com.example.testapp.profile_fragment.CreateTripActivity.this, ProfileFragment.class);
//                    startActivity(returnIntent);
//                }
//
//            }
//        });


    }

    protected void createToast(String message) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, message, duration);
        toast.show();
    }

    protected String extractText(EditText field) {
        return field.getText().toString().trim();
    }

    protected boolean isAllFieldsFilled(ArrayList<EditText> allFields) {
        for (EditText formField : allFields) {
            String message = extractText(formField);
            if (message.equals("")) {
                return false;
            }
        }
        return true;
    }

    protected boolean isNumericallyFilled(EditText field) {
        String message = extractText(field);

        for (char dig : message.toCharArray()) {
            if (!Character.isDigit(dig)) {
                return false;
            }
        }
        return true;
    }

    protected boolean isValidLowerToUpperRange(EditText lower, EditText upper) {
        Integer lowerVal = Integer.parseInt(extractText(lower));
        Integer upperVal = Integer.parseInt(extractText(upper));
        return lowerVal < upperVal;
    }

    protected Integer extractInt(EditText numericalField){
        return Integer.parseInt(extractText(numericalField));
    }

    protected String createDescriptionForTripModel(EditText country, EditText startDate, EditText endDate) {
        String desc = "";
        desc += "Country:\n";
        desc += extractText(country) + "\n";
        desc += "Trip Duration:\n";
        desc += extractText(startDate) + " - " + extractText(endDate);
        return desc;
    }

}