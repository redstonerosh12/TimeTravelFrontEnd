package com.example.testapp.middleware;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.activity.ComponentActivity;

import com.example.testapp.LoginPage;
import com.example.testapp.MainActivity;
import com.example.testapp.api.API;
import com.example.testapp.api.Service;
import com.example.testapp.model.Token;
import com.example.testapp.model.User;

public class Auth {
    private final static String TAG = "Auth";
    private static String username;
    private static Auth instance;
    private static String token;
    private static SharedPreferences mPreferences;
    private final String TOKEN_KEY = "Token_key";
    private final String USERNAME_KEY = "Username_key";

    private Auth(SharedPreferences sharedPreferences) {
        mPreferences = sharedPreferences;
        refresh();
    }

    public static Auth getInstance() {
        return instance;
    }

    public static Auth getInstance(ComponentActivity activity) {
        if (instance == null)
            instance = new Auth(activity.getSharedPreferences("com.example.android.testapp", Context.MODE_PRIVATE));
        String className = activity.getClass().getSimpleName();
        boolean authenticated = instance.isAuth();
        Log.e(TAG, className);
        if (className.equals("SignupPage") || className.equals("LoginPage")) {
            if (authenticated) activity.startActivity(new Intent(activity, MainActivity.class));
        } else if (!authenticated) activity.startActivity(new Intent(activity, LoginPage.class));
        return instance;
    }

    public boolean isAuth() {
        //TODO: Check if token is valid
        return token != null;
    }

    private void refresh() {
        token = mPreferences.getString(TOKEN_KEY, null);
        username = mPreferences.getString(USERNAME_KEY, null);
        Log.e("Auth:refresh", username + " " + token);
    }

    public void saveUserAndToken(String username, String token) {
        Auth.token = token;
        Auth.username = username;
        Log.e("Auth:storing", username + " " + token);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(TOKEN_KEY, token);
        preferencesEditor.putString(USERNAME_KEY, username);
        preferencesEditor.apply();
    }

    public void login(String username, String password, API.Callback<Token> callback) {
        API.Auth.authenticate(new User(username, password))
                .setOnResponse(tokenRes -> {
                    String token = tokenRes.getToken();
                    saveUserAndToken(username, token);
                    Log.i(TAG + ":login", token);
                    callback.onResponse(tokenRes);
                    callback.onFinal();
                }).setOnFailure(response -> {
                    callback.onFailure(response);
                    callback.onFinal();
                }).fetch();
    }

    public void register(String username, String password, String email, API.Callback<Token> callback) {
        Log.e(TAG, "Registering");
        API.Auth.register(username, password, email)
                .setOnResponse(token -> {
                    saveUserAndToken(username, token.getToken());
                    callback.onResponse(token);
                    callback.onFinal();
                })
                .setOnFailure(response -> {
                    callback.onFailure(response);
                    Log.e(TAG, "Fail to Register");
                    callback.onFinal();
                })
                .fetch();
    }

    public void logout(com.example.testapp.api.Response<Service.Message> callback) {
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(TOKEN_KEY, null);
        preferencesEditor.putString(USERNAME_KEY, null);
        preferencesEditor.apply();
        final String LOGOUT_CODE = TAG + ":logout";
        Log.i(LOGOUT_CODE, "Logging out");
        Log.i(LOGOUT_CODE, token);
        String tokenHolder = token;
        token = null;
        username = null;
        API.Auth.logOut(getToken(tokenHolder)).setOnResponse(message -> {
            Log.i(LOGOUT_CODE, "Log out Successful");
            callback.onResponse(message);
        }).setOnFailure(response -> {
            Log.i(LOGOUT_CODE, response.toString());
        }).fetch();
    }

    public String getUsername() {
        return Auth.username;
    }

    public String getToken() {
        return "Bearer " + getTokenRaw();
    }

    public String getToken(String token) {
        return "Bearer " + token;
    }

    public String getTokenRaw() {
        return token;
    }
}
