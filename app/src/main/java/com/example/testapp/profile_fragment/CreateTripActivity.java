package com.example.testapp.profile_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatButton;

import com.example.testapp.MainActivity;
import com.example.testapp.R;
import com.example.testapp.lib.AppCompatActivity;
import com.example.testapp.model.lib.DateTime;
import com.example.testapp.model.TravelPlan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateTripActivity extends AppCompatActivity {
    public CreateTripActivity() {
        super(R.layout.create_trip_form);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EditText editTitle = findViewById(R.id.editTitle);
        EditText editStartDate = findViewById(R.id.editStartDate);
        EditText editEndDate = findViewById(R.id.editEndDate);
        ArrayList<EditText> allFields = new ArrayList<>(Arrays.asList(editTitle, editStartDate, editEndDate));

        AppCompatButton submitForm = findViewById(R.id.SubmitFormCreateTrip);

        submitForm.setOnClickListener(view -> {
            if (!isAllFieldsFilled(allFields)) {
                toast.makeShort("All fields must be filled in");
            } else if (!isNumericallyFilled(editStartDate)) {
                toast.makeShort("Start date must be numerical!");
            } else if (!isNumericallyFilled(editEndDate)) {
                toast.makeShort("End date must be numerical!");
            } else {
                String tripHeader = extractText(editTitle);
                String startDate = startDateForTripModel(editStartDate);
                String endDate = startDateForTripModel(editEndDate);

                //FIXME: To add from frontend
                String startDay = "12";
                String startMonth = "04";
                String startYear = "2024";

                String endDay = "13";
                String endMonth = "04";
                String endYear = "2024";

                LocalDate start = DateTime.stringToDate(startYear, startMonth, startDay);
                LocalDate end = DateTime.stringToDate(endYear, endMonth, endDay);

                TravelPlan.create(tripHeader, start, end).setOnResponse(travelPlan -> {
                    Log.d(TAG, travelPlan.toString());
                    toast.makeLong("Trip Created");
                    Intent returnIntent = new Intent(CreateTripActivity.this, MainActivity.class);
                    startActivity(returnIntent);
                }).setOnFailure(res -> {
                    toast.makeLong("Failed to create trip");
                }).fetch();
            }
        });
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

    protected String startDateForTripModel(EditText startDate) {
        String desc = "";
        desc += extractText(startDate);
        return desc;
    }

    protected String endDateTripModel(EditText endDate) {
        String desc = "";
        desc += extractText(endDate);
        return desc;
    }
}