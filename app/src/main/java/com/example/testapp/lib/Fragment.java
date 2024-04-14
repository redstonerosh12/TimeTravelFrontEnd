package com.example.testapp.lib;

import android.content.Intent;
import android.os.Bundle;

import com.example.testapp.model.lib.Toast;

public class Fragment extends androidx.fragment.app.Fragment {
    protected final static ConfigurationManager config = ConfigurationManager.getInstance();
    protected final String TAG;
    protected Toast toast;

    public Fragment(int contentLayoutId) {
        super(contentLayoutId);
        TAG = this.getClass().getSimpleName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toast = new com.example.testapp.model.lib.Toast(getActivity());
    }

    protected void goTo(Class<?> to) {
        startActivity(new Intent(requireContext(), to));
    }
}
