package com.example.testapp.profile_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.testapp.MainActivity;
import com.example.testapp.R;
import com.example.testapp.api.API;
import com.example.testapp.home_fragment.CreateEventActivity;
import com.example.testapp.lib.AppCompatActivity;
import com.example.testapp.middleware.Auth;
import com.example.testapp.model.EventModel;
import com.example.testapp.model.TravelPlan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateTravelPlanActivity extends AppCompatActivity {
    public CreateTravelPlanActivity() {
        super(R.layout.create_travel_plan_form);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_travel_plan_form);

        EditText editTitle = findViewById(R.id.editTitle);
        EditText editStartDay = findViewById(R.id.editStartDay);
        EditText editStartMonth = findViewById(R.id.editStartMonth);
        EditText editStartYear = findViewById(R.id.editStartYear);
        EditText editEndDay = findViewById(R.id.editEndDay);
        EditText editEndMonth = findViewById(R.id.editEndMonth);
        EditText editEndYear = findViewById(R.id.editEndYear);
        ArrayList<EditText> allFields = new ArrayList<>(Arrays.asList(editTitle, editStartDay, editStartMonth, editStartYear, editEndDay, editEndMonth, editEndYear));

        AppCompatButton submitForm = findViewById(R.id.SubmitFormCreateTravelPlan);

        submitForm.setOnClickListener(v-> {
            if (!isAllFieldsFilled(allFields)) {
                toast.makeShort("All fields must be filled in");
            } else if (!isNumericallyFilled(editStartDay)) {
                toast.makeShort("Start date must be numerical!");
            } else if (!isNumericallyFilled(editStartMonth)) {
                toast.makeShort("Start date must be numerical!");
            } else if (!isNumericallyFilled(editStartYear)) {
                toast.makeShort("Start date must be numerical!");
            } else if (!isNumericallyFilled(editEndDay)) {
                toast.makeShort("Start date must be numerical!");
            } else if (!isNumericallyFilled(editEndMonth)) {
                toast.makeShort("Start date must be numerical!");
            } else if (!isNumericallyFilled(editEndYear)) {
                toast.makeShort("Start date must be numerical!");
            }

            String title = extractText(editTitle);
            LocalDate startDate = LocalDate.of(extractTextToInt(editStartYear),extractTextToInt(editStartMonth),extractTextToInt(editStartDay));
            LocalDate endDate = LocalDate.of(extractTextToInt(editEndYear),extractTextToInt(editEndMonth),extractTextToInt(editEndDay));

            TravelPlan.create(title, startDate, endDate).setOnResponse(travelPlan -> {
                Log.d(TAG, travelPlan.toString());
                toast.makeLong("Trip Created");
                Intent returnIntent = new Intent(CreateTravelPlanActivity.this, MainActivity.class);
                startActivity(returnIntent);
            }).setOnFailure(res -> {
                toast.makeLong("Failed to create trip");
            }).fetch();
        });
    }

    protected String extractText(EditText field) {
        return field.getText().toString().trim();
    }
    protected int extractTextToInt(EditText field) {
        return Integer.parseInt(field.getText().toString().trim());
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

    protected String DateForTravelPlanModel(EditText startDay, EditText startMonth, EditText startYear) {
        String desc = extractText(startDay) + "/" + extractText(startMonth) + "/" + extractText(startYear);
        return desc;
    }

}