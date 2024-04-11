package com.example.testapp.home_fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.testapp.R;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.testapp.api.API;
import com.example.testapp.home_fragment.concrete_fragment.ConcreteFragment;
import com.example.testapp.home_fragment.suggested_fragment.SuggestedFragment;
import com.example.testapp.middleware.Auth;
import com.example.testapp.model.EventModel;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateEventActivity extends AppCompatActivity {
    private final String CREATEEVENT = "CreateEvent";
    protected boolean isOwnerOfTravelPlan = true; //TODO get boolean from database

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
        EditText editStartDay = findViewById(R.id.editDay);
        EditText editStartMonth = findViewById(R.id.editMonth);
        EditText editStartYear = findViewById(R.id.editYear);
        EditText editAddress = findViewById(R.id.editAddress);
        EditText editReasonVisit = findViewById(R.id.editReasonVisit);
        EditText editLowerCost = findViewById(R.id.editLowerCost);
        EditText editUpperCost = findViewById(R.id.editUpperCost);
        ArrayList<EditText> allFields = new ArrayList<>(Arrays.asList(editTitle, editStartTime, editEndTime, editAddress, editReasonVisit, editLowerCost, editUpperCost));

        AppCompatButton submitForm = findViewById(R.id.SubmitFormCreateEvent);

        if (!isOwnerOfTravelPlan) {
            concreteOrSuggested.setVisibility(View.GONE);
            RadioButton suggestedRadioButton = findViewById(R.id.radioButtonSuggested);
            suggestedRadioButton.setChecked(true);
        }

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
                } else if (!isNumericallyFilled(editStartDay)) {
                    createToast("Day should be numerical!");
                } else if (!isNumericallyFilled(editStartMonth)) {
                    createToast("Month should be numerical!");
                } else if (!isNumericallyFilled(editStartYear)) {
                    createToast("Year should be numerical!");
                } else if (!isNumericallyFilled(editLowerCost)) {
                    createToast("Lower cost must be numerical!");
                } else if (!isNumericallyFilled(editUpperCost)) {
                    createToast("Upper cost must be numerical!");
                } else if (!isValidLowerToUpperRange(editStartTime, editEndTime)) {
                    createToast("Start time should be before the End time!");
                } else if (!isValidLowerToUpperRange(editLowerCost, editUpperCost)) {
                    createToast("Lower cost range should be lesser than the Upper cost range!");
                } else {
                    try {
                        Integer year = extractInt(editStartYear);
                        Integer month = extractInt(editStartMonth);
                        Integer day = extractInt(editStartDay);
                        String startTime = extractText(editStartTime);
                        String endTime = extractText(editEndTime);
                        int startHour = Integer.parseInt(startTime.substring(0, 2));
                        int startMin = Integer.parseInt(startTime.substring(2));
                        int endHour = Integer.parseInt(endTime.substring(0, 2));
                        int endMin = Integer.parseInt(endTime.substring(2));

                        LocalDateTime eventStartTimeInDateTime = LocalDateTime.of(year, month, day, startHour, startMin, 0, 0);
                        LocalDateTime eventEndTimeInDateTime = LocalDateTime.of(year, month, day, endHour, endMin, 0, 0);

                        if (eventStartTimeInDateTime != null && eventEndTimeInDateTime != null) {
                            Log.d(CREATEEVENT, "Passed all the form checks");
                            if (selectedEventTypeId == radioConcreteId || selectedEventTypeId == radioSuggestedId) {
                                EventModel.Status status = selectedEventTypeId == radioConcreteId ? EventModel.Status.CONCRETE : EventModel.Status.SUGGESTED;
                                String travelPlanID = "1"; //FIXME: Get from global variable
                                String title = extractText(editTitle);
                                String location = extractText(editAddress);
                                String description = createDescriptionForEventModel(editAddress, editReasonVisit, editLowerCost, editUpperCost);
                                EventModel.Create creatingEvent = new EventModel.Create(title, eventStartTimeInDateTime, eventEndTimeInDateTime, description, status, location);
                                Log.d(CREATEEVENT + ":Creating", creatingEvent.toString());

                                API.Event.create(Auth.getInstance(), travelPlanID, creatingEvent)
                                        .setOnResponse(eventGet -> {
                                            EventModel event = eventGet.getEvent();
                                            Log.d(CREATEEVENT + ":Created", event.toString());
                                            Toast.makeText(CreateEventActivity.this, "Event Created",Toast.LENGTH_LONG).show();
                                            //FIXME: Crash with intent
                                            //Intent returnIntent = new Intent(CreateEventActivity.this, status == EventModel.Status.CONCRETE ? ConcreteFragment.class : SuggestedFragment.class);
                                            //startActivity(returnIntent);
                                        })
                                        .setOnFailure(res -> {
                                        }).fetch();
                            }
                        }
                    } catch (DateTimeException e) {
                        createToast("Date and/or time input is invalid!");
                    }
                }
                //TODO: make check for data validity, Concrete selected time cannot clash with existent concrete time etc.
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

    protected Integer extractInt(EditText numericalField) {
        return Integer.parseInt(extractText(numericalField));
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