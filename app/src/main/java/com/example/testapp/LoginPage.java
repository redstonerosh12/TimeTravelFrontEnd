package com.example.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class LoginPage extends AppCompatActivity {
    EditText usernameInput;
    EditText passwordInput;
    MaterialButton loginbtn;
    MaterialButton signupbtn;

    ProgressBar progressBar;
    public static String myuserkey = "User:";
    public static String mypasswordkey = "Password:";
    public static String myemailkey = "Email:";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = findViewById(R.id.progress_circular);
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        signupbtn = findViewById(R.id.sign_up);
        loginbtn = findViewById(R.id.login);

        loginbtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, MainActivity.class);

                //TODO 1.1 replace "user" and "passwword" with account's actual username and password
                if (usernameInput.getText().toString().equals("user") && passwordInput.getText().toString().equals("password")) {
                    progressBar.setVisibility(View.VISIBLE);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);

                } else {
                    Toast.makeText(LoginPage.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
                Log.d("Test Credentials", "User:" + usernameInput.getText().toString());
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new  Intent ( LoginPage.this, SignupPage.class);
                startActivity(intent);



                Log.d("Test Credentials", "User:" + usernameInput.getText().toString());
            }
        });
    }
}