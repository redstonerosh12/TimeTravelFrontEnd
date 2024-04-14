package com.example.testapp.lib;

import com.example.testapp.model.lib.Toast;

public class AppCompatActivity extends androidx.appcompat.app.AppCompatActivity {
    protected final static ConfigurationManager config = ConfigurationManager.getInstance();
    protected final String TAG;
    protected Toast toast;

    public AppCompatActivity(int contentLayoutId) {
        super(contentLayoutId);
        TAG = this.getClass().getSimpleName();
    }
}
