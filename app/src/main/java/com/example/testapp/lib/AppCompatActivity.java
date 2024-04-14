package com.example.testapp.lib;

import android.os.Bundle;

import com.example.testapp.model.lib.Toast;

public class AppCompatActivity extends androidx.appcompat.app.AppCompatActivity {
    protected final static ConfigurationManager config = ConfigurationManager.getInstance();
    protected final String TAG;
    protected Toast toast;

    public AppCompatActivity() {
        super();
        TAG = this.getClass().getSimpleName();
    }

    public AppCompatActivity(int contentLayoutId) {
        super(contentLayoutId);
        TAG = this.getClass().getSimpleName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toast = new com.example.testapp.model.lib.Toast(this);
    }
}
