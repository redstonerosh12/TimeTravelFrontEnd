package com.example.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testapp.api.API;
import com.example.testapp.middleware.Auth;
import com.example.testapp.model.Token;
import com.google.android.material.button.MaterialButton;

import retrofit2.Response;

public class LoginPage extends AppCompatActivity {
    EditText usernameInput;
    EditText passwordInput;
    MaterialButton loginbtn;
    MaterialButton signupbtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Auth auth = Auth.getInstance(this);
        if (!auth.isAuth()) {
            setContentView(R.layout.activity_login);

            progressBar = findViewById(R.id.progress_circular);
            usernameInput = findViewById(R.id.username_input);
            passwordInput = findViewById(R.id.password_input);
            signupbtn = findViewById(R.id.sign_up);
            loginbtn = findViewById(R.id.login);

            loginbtn.setOnClickListener(v -> {
                progressBar.setVisibility(View.VISIBLE);
                usernameInput.setEnabled(false);
                passwordInput.setEnabled(false);
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    if (username.isEmpty())
                        Toast.makeText(this, "Username Empty", Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Password Empty", Toast.LENGTH_SHORT).show();
                    usernameInput.setEnabled(false);
                    passwordInput.setEnabled(false);
                    progressBar.setVisibility(View.GONE);
                } else {
                    auth.login(username, password, new API.Callback<Token>() {
                        @Override
                        public void onResponse(Token token) {
                            Toast.makeText(LoginPage.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginPage.this, MainActivity.class));
                        }

                        @Override
                        public void onFailure(Response<Token> res) {
                            Toast.makeText(LoginPage.this, "Wrong Username/Password", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFinal() {
                            usernameInput.setEnabled(false);
                            passwordInput.setEnabled(false);
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            });

            signupbtn.setOnClickListener(v -> {
                Intent intent = new Intent(LoginPage.this, SignupPage.class);
                startActivity(intent);
            });
        }
    }
}