package com.example.testapp.profile_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.testapp.R;

public class JoinTripActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_trip_form);

        EditText editTitle = findViewById(R.id.editTitle);
//        ArrayList<EditText> allFields = new ArrayList<>(Collections.singletonList(editTitle));

        AppCompatButton submitForm = findViewById(R.id.SubmitFormJoinTrip);
        submitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isAllFieldsFilled(editTitle)) {
                    createToast("All fields must be filled in");
                }

                Intent returnIntent = new Intent(JoinTripActivity.this, ProfileFragment.class);
                startActivity(returnIntent);
            }
        });
    }

        protected void createToast (String message){
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, message, duration);
            toast.show();
        }

        protected String extractText (EditText field){
            return field.getText().toString().trim();
        }

        protected boolean isAllFieldsFilled (EditText editTitle){
            String message = extractText(editTitle);
            return !message.equals("");
        }


        protected boolean isNumericallyFilled (EditText field){
            String message = extractText(field);

            for (char dig : message.toCharArray()) {
                if (!Character.isDigit(dig)) {
                    return false;
                }
            }
            return true;
        }

}