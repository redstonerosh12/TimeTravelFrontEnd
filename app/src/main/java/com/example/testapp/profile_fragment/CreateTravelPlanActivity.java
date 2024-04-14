package com.example.testapp.profile_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.testapp.MainActivity;
import com.example.testapp.R;
import com.example.testapp.api.API;
import com.example.testapp.home_fragment.CreateEventActivity;
import com.example.testapp.middleware.Auth;
import com.example.testapp.model.EventModel;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateTravelPlanActivity extends AppCompatActivity {
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

        submitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isAllFieldsFilled(allFields)) {
                    createToast("All fields must be filled in");
                } else if (!isNumericallyFilled(editStartDay)) {
                    createToast("Start date must be numerical!");
                } else if (!isNumericallyFilled(editStartMonth)) {
                    createToast("Start date must be numerical!");
                } else if (!isNumericallyFilled(editStartYear)) {
                    createToast("Start date must be numerical!");
                } else if (!isNumericallyFilled(editEndDay)) {
                    createToast("Start date must be numerical!");
                } else if (!isNumericallyFilled(editEndMonth)) {
                    createToast("Start date must be numerical!");
                } else if (!isNumericallyFilled(editEndYear)) {
                    createToast("Start date must be numerical!");
                }

                String travelPlanTitle = extractText(editTitle);
                String startDate = DateForTravelPlanModel(editStartDay, editStartMonth, editStartYear);
                String endDate = DateForTravelPlanModel(editEndDay, editEndMonth, editEndYear);

                TravelPlanModel.CreateTravelPlan creatingTravelPlan = new TravelPlanModel.CreateTravelPlan(travelPlanTitle, startDate, endDate);

                TravelPlanModel newTravelPlan = new TravelPlanModel(travelPlanTitle, startDate, endDate);

                Intent returnIntent = new Intent(CreateTravelPlanActivity.this, ProfileFragment.class);
                startActivity(returnIntent);
            }
        });
    }

    private void createTravelPlan(String travelPlanID, TravelPlanModel.CreateTravelPlan creatingTravelPlan) {
        API.TravelPlans.create(Auth.getInstance(), creatingTravelPlan)
                .setOnResponse(eventGet -> {
                    TravelPlanModel event = travelPlanGet.getTravelPlan();
                    Toast.makeText(CreateTravelPlanActivity.this, "Event Created", Toast.LENGTH_LONG).show();
                    Intent returnIntent = new Intent(CreateTravelPlanActivity.this, MainActivity.class);
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

    protected Integer extractInt(EditText numericalField){
        return Integer.parseInt(extractText(numericalField));
    }

    protected String DateForTravelPlanModel(EditText startDay, EditText startMonth, EditText startYear) {
        String desc = extractText(startDay) + "/" + extractText(startMonth) + "/" + extractText(startYear);
        return desc;
    }

}