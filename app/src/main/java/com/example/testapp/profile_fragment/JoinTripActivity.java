package com.example.testapp.profile_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.testapp.MainActivity;
import com.example.testapp.R;
import com.example.testapp.model.TravelPlan;
import com.example.testapp.model.lib.Toast;

public class JoinTripActivity extends AppCompatActivity {
    private final String TAG = "JoinTripActivity";
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toast = new Toast(this);

        setContentView(R.layout.join_trip_form);

        EditText editTitle = findViewById(R.id.editTitle);

        AppCompatButton submitForm = findViewById(R.id.SubmitFormJoinTrip);

        submitForm.setOnClickListener(view -> {
            String joinCode = editTitle.getText().toString();
            if (joinCode.isEmpty()) toast.makeShort("All fields must be filled in");
            else {
                Log.d(TAG, "Joining: " + joinCode);
                TravelPlan.join(joinCode)
                        .setOnResponse(travelPlan -> {
                            toast.makeShort("Trip Joined");
                            Intent returnIntent = new Intent(JoinTripActivity.this, MainActivity.class);
                            startActivity(returnIntent);
                        })
                        .setOnFailure(res -> {
                            Log.e(TAG, "Error Message: " + res.message());
                            if (res.code() == 404) toast.makeShort("No such trip");
                            else toast.makeShort("Failed to join");
                        }).fetch();
            }
        });
    }
}