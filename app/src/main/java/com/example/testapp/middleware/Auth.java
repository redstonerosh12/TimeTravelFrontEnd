package com.example.testapp.middleware;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.testapp.api.API;
import com.example.testapp.api.Service;
import com.example.testapp.model.Token;
import com.example.testapp.model.User;

public class Auth {
    private static String username;
    private static Auth instance;
    private static String token;
    private static SharedPreferences mPreferences;
    private final String TOKEN_KEY = "Token_key";
    private final String USERNAME_KEY = "Username_key";
    private final String AUTH_CODE = "Auth";

    private Auth(SharedPreferences sharedPreferences) {
        mPreferences = sharedPreferences;
        refresh();
    }

    public static Auth getInstance(SharedPreferences sharedPreferences) {
        if (instance == null) instance = new Auth(sharedPreferences);
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
                    Log.i(AUTH_CODE + ":login", token);
                    callback.onResponse(tokenRes);
                    callback.onFinal();
                }).setOnFailure(response -> {
                    callback.onFailure(response);
                    callback.onFinal();
                }).fetch();
    }

    public void register(String username, String password, String email) {
        Log.e(AUTH_CODE, "Registering");
        API.Auth.register(username, password, email)
                .setOnResponse(token -> saveUserAndToken(username, token.getToken()))
                .setOnFailure(response -> Log.e(AUTH_CODE, "Fail to Register"))
                .fetch();
    }

    public void logout(com.example.testapp.api.Response<Service.Message> callback) {
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(TOKEN_KEY, null);
        preferencesEditor.putString(USERNAME_KEY, null);
        preferencesEditor.apply();
        final String LOGOUT_CODE = AUTH_CODE + ": logout";
        Log.i(LOGOUT_CODE, "Logging out");
        Log.i(LOGOUT_CODE, token);
        String tokenHolder = token;
        token = null;
        username = null;
        API.Auth.logOut(getToken(tokenHolder)).setOnResponse(message -> {
            Log.i(LOGOUT_CODE, "Log out Successful");
            callback.onResponse(message);
        }).setOnFailure(response -> {
            Log.i(LOGOUT_CODE + ":logout", response.toString());
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
