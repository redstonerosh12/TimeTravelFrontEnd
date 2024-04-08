package com.example.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class SignupPage extends AppCompatActivity {

    EditText createUsername, createPassword, confirmPassword, Email;
    MaterialButton createAccount, goBack;
    ProgressBar progressBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        createUsername = findViewById(R.id.username_create);
        createPassword = findViewById(R.id.password_create);
        confirmPassword = findViewById(R.id.confirm_password);
        Email = findViewById(R.id.email);
        createAccount = findViewById(R.id.create_account);
        goBack = findViewById(R.id.go_back);
        progressBar = findViewById(R.id.progress_circular);
        createAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //TODO 1.0 email address valid check
                String username = createUsername.getText().toString();
                String password = createPassword.getText().toString();
                String confirm_password = confirmPassword.getText().toString();
                String email = Email.getText().toString();
                //TODO 1.0 email address valid check


                if (email.isEmpty()) {
                    Toast.makeText(SignupPage.this, "please enter your email address", Toast.LENGTH_SHORT).show();
                    Email.setError("need your email");
                    Email.requestFocus();
                }
                else if(!(Patterns.EMAIL_ADDRESS.matcher(email).matches())){
                    Toast.makeText(SignupPage.this, "please enter a valid email address", Toast.LENGTH_SHORT).show();
                    Email.setError("invalid email");
                    Email.requestFocus();
                }
                else if (username.isEmpty()) {
                    Toast.makeText(SignupPage.this, "please enter a username", Toast.LENGTH_SHORT).show();
                    createUsername.setError("invalid username");
                    createUsername.requestFocus();
                }
                //TODO 1.1 replace "user" with account usernames inside the database for username taken
                else if (username.equals("user")) {
                    Toast.makeText(SignupPage.this, "username taken", Toast.LENGTH_SHORT).show();
                    createUsername.setError("invalid username");
                    createUsername.requestFocus();
                }else if (password.isEmpty()) {
                    Toast.makeText(SignupPage.this, "please enter a password", Toast.LENGTH_SHORT).show();
                    createPassword.setError("create password");
                    createPassword.requestFocus();}
                else if (password.length() < 6) {
                    Toast.makeText(SignupPage.this, "please enter a password with at least six characters", Toast.LENGTH_SHORT).show();
                    createPassword.setError("minimum 6 characters");
                    createPassword.requestFocus();
                }else if (!(confirm_password.equals(password))) {
                    Toast.makeText(SignupPage.this, "your password and confirmation password must match", Toast.LENGTH_SHORT).show();
                    confirmPassword.setError("password does not match");
                    confirmPassword.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(SignupPage.this, MainActivity.class);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);

                }
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupPage.this, LoginPage.class);
                startActivity(intent);
                }
        });
    }
}