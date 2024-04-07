package com.example.testapp.home_fragment;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.testapp.home_fragment.HomeFragment;
import com.example.testapp.home_fragment.concrete_fragment.ConcreteEventModel;
import com.example.testapp.home_fragment.concrete_fragment.ConcreteFragment;
import com.example.testapp.home_fragment.suggested_fragment.SuggestedEventModel;
import com.example.testapp.profile_fragment.ProfileFragment;
import com.google.android.material.navigation.NavigationBarView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event_form);

        RadioGroup concreteOrSuggested = findViewById(R.id.radioConcreteSuggestedOption);
        int radioConcreteId = R.id.radioButtonConcrete;
        int radioSuggestedId = R.id.radioButtonSuggested;
        EditText editTitle = findViewById(R.id.editTitle);
        EditText editStartTime = findViewById(R.id.editStartTime);
        EditText editEndTime = findViewById(R.id.editEndTime);
        EditText editAddress = findViewById(R.id.editAddress);
        EditText editReasonVisit = findViewById(R.id.editReasonVisit);
        EditText editLowerCost = findViewById(R.id.editLowerCost);
        EditText editUpperCost = findViewById(R.id.editUpperCost);
        ArrayList<EditText> allFields = new ArrayList<>(Arrays.asList(editTitle, editStartTime, editEndTime, editAddress, editReasonVisit, editLowerCost, editUpperCost));

        AppCompatButton submitForm = findViewById(R.id.SubmitFormCreateEvent);

        submitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedEventTypeId = concreteOrSuggested.getCheckedRadioButtonId();

                if (selectedEventTypeId == -1) {
                    createToast("Event type concrete or suggested must be selected!");
                } else if (!isAllFieldsFilled(allFields)) {
                    createToast("All fields must be filled in");
                } else if (!isNumericallyFilled(editStartTime)) {
                    createToast("Start time must be numerical!");
                } else if (!isNumericallyFilled(editEndTime)) {
                    createToast("End time must be numerical!");
                } else if (!isNumericallyFilled(editLowerCost)) {
                    createToast("Lower cost must be numerical!");
                } else if (!isNumericallyFilled(editUpperCost)) {
                    createToast("Upper cost must be numerical!");
                } else if (!isValidLowerToUpperRange(editStartTime, editEndTime)) {
                    createToast("Start time should be before the End time!");
                } else if (!isValidLowerToUpperRange(editLowerCost, editUpperCost)) {
                    createToast("Lower cost range should be lesser than the Upper cost range!");
                }


                //TODO: make check for data validity, Concrete selected time cannot clash with existent concrete time etc.

                else {
                    Log.d("CreateEvent", "Passed all the form checks");
                    //make the concrete event model
                    String eventTime = extractText(editStartTime) + " - " + extractText(editEndTime);
                    String eventHeader = extractText(editTitle);
                    String eventDescription = createDescriptionForEventModel(editAddress, editReasonVisit, editLowerCost, editUpperCost);
                    String eventID = "TODO MIDDLEWARE";
                    boolean eventOwnedByUser = true; //TODO see how to pass info of user token who created to database

                    if (selectedEventTypeId == radioConcreteId) {
                        ConcreteEventModel newEvent = new ConcreteEventModel(eventTime, eventHeader, eventDescription, eventID, eventOwnedByUser);
                        Log.d("CreateEvent.NewEvent", "Made a concrete model");
                    }
                    else if (selectedEventTypeId == radioSuggestedId){
                        SuggestedEventModel newEvent = new SuggestedEventModel(eventTime, eventHeader, eventDescription, eventID, eventOwnedByUser);
                        Log.d("CreateEvent.NewEvent", "Made a suggested model");

                    }
                    Log.d("CreateEvent.NewEvent.Data", eventTime + eventHeader + eventDescription + eventID + eventOwnedByUser);

                    Intent returnIntent = new Intent(CreateEventActivity.this, ConcreteFragment.class);
                    startActivity(returnIntent);
                }

            }
        });


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

    protected String createDescriptionForEventModel(EditText address, EditText reasonForVisit, EditText lowerCost, EditText upperCost) {
        String desc = "";
        desc += "Address:\n";
        desc += extractText(address) + "\n";
        desc += "Reason For Visit:\n";
        desc += extractText(reasonForVisit) + "\n";
        desc += "Cost Range:\n";
        desc += extractText(lowerCost) + " - " + extractText(upperCost);
        return desc;
    }


}