package com.example.testapp.home_fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.testapp.MainActivity;
import com.example.testapp.R;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.testapp.api.API;
import com.example.testapp.api.DataSource;
import com.example.testapp.middleware.Auth;
import com.example.testapp.model.EventModel;
import com.example.testapp.model.lib.DateTimeAVL;
import com.example.testapp.model.lib.StartEndDateTime;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Response;

public class CreateEventActivity extends AppCompatActivity {
    private final String TAG = "CreateEvent";
    protected boolean isOwnerOfTravelPlan = true; //TODO get boolean from database
    private DateTimeAVL dtAVL;
    private LocalDate selectedDate;

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
        //Testing
        editTitle.setText("asd");
        editStartTime.setText("1100");
        editEndTime.setText("1200");
        editStartDay.setText("12");
        editStartMonth.setText("4");
        editStartYear.setText("2024");
        editAddress.setText("2024");
        editReasonVisit.setText("2024");
        editLowerCost.setText("1");
        editUpperCost.setText("2");


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
                            Log.d(TAG, "Passed all the form checks");
                            if (selectedEventTypeId == radioConcreteId || selectedEventTypeId == radioSuggestedId) {
                                String travelPlanID = "1"; //FIXME: Get from global variable
                                String title = extractText(editTitle);
                                String location = extractText(editAddress);
                                String description = createDescriptionForEventModel(editAddress, editReasonVisit, editLowerCost, editUpperCost);
                                EventModel.Status status = selectedEventTypeId == radioConcreteId ? EventModel.Status.CONCRETE : EventModel.Status.SUGGESTED;
                                EventModel.Create creatingEvent = new EventModel.Create(title, eventStartTimeInDateTime, eventEndTimeInDateTime, description, status, location);

                                Log.d(TAG + ":Creating", creatingEvent.toString());

                                // Check for conflict only for Concrete Events
                                if (status == EventModel.Status.CONCRETE) {
                                    StartEndDateTime set = new StartEndDateTime(eventStartTimeInDateTime, eventEndTimeInDateTime);
                                    Log.e(TAG, set.toString());
                                    LocalDate currentDate = LocalDate.of(year, month, day);
                                    if (dtAVL == null || !selectedDate.equals(currentDate)) {
                                        Log.e("MainActivity", "Renew AVL");
                                        selectedDate = currentDate;
                                        DataSource.getEventsByDate(travelPlanID, selectedDate, EventModel.Status.CONCRETE, new API.Callback<ArrayList<EventModel>>() {
                                            @Override
                                            public void onFailure(Response<ArrayList<EventModel>> response) {
                                                Log.e("MainActivity", "FAILED");
                                            }

                                            @Override
                                            public void onResponse(ArrayList<EventModel> events) {
                                                if (events.isEmpty())
                                                    createEvent(travelPlanID, creatingEvent);
                                                else {
                                                    dtAVL = new DateTimeAVL(events);
                                                    Log.e("MainActivity", set.toString());
                                                    boolean conflict = dtAVL.checkConflict(set);
                                                    Log.e("MainActivity", Boolean.toString(conflict));
                                                    if (conflict)
                                                        createToast("Starttime and Endtime is conflicting with another event");
                                                    else createEvent(travelPlanID, creatingEvent);
                                                }
                                            }
                                        });
                                    } else {
                                        boolean conflict = dtAVL.checkConflict(set);
                                        if (conflict) {
                                            createToast("Starttime and Endtime is conflicting with another event");
                                        } else createEvent(travelPlanID, creatingEvent);
                                    }
                                } else createEvent(travelPlanID, creatingEvent);
                            }
                        }
                    } catch (DateTimeException e) {
                        createToast("Date and/or time input is invalid!");
                    }
                }
            }
        });
    }

    private void createEvent(String travelPlanID, EventModel.Create creatingEvent) {
        API.Event.create(Auth.getInstance(), travelPlanID, creatingEvent)
                .setOnResponse(eventGet -> {
                    EventModel event = eventGet.getEvent();
                    Log.d(TAG + ":Created", event.toString());
                    Toast.makeText(CreateEventActivity.this, "Event Created", Toast.LENGTH_LONG).show();
                    //FIXME: Change Intent to go back to Concrete or Suggested
                    Intent returnIntent = new Intent(CreateEventActivity.this, MainActivity.class);
                    startActivity(returnIntent);
                })
                .setOnFailure(res -> {
                }).fetch();
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