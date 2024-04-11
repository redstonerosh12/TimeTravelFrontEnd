package com.example.testapp.profile_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.testapp.R;

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

        AppCompatButton submitForm = findViewById(R.id.SubmitFormCreateTrip);

        submitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isAllFieldsFilled(allFields)) {
                    createToast("All fields must be filled in");
                } else if (!isNumericallyFilled(editStartDate)) {
                    createToast("Start date must be numerical!");
                } else if (!isNumericallyFilled(editEndDate)) {
                    createToast("End date must be numerical!");
                }

                String tripHeader = extractText(editTitle);
                String startDate = startDateForTripModel(editStartDate);
                String endDate = startDateForTripModel(editEndDate);
                TripModel newTrip = new TripModel(tripHeader, startDate, endDate);

                Intent returnIntent = new Intent(CreateTripActivity.this, ProfileFragment.class);
                startActivity(returnIntent);
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

    protected Integer extractInt(EditText numericalField){
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