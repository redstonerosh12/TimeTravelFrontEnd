package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.testapp.MainFragments.CalendarFragment;
import com.example.testapp.databinding.ActivityMainBinding;
import com.example.testapp.home_fragment.HomeFragment;
import com.example.testapp.profile_fragment.ProfileFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    public Boolean selectedPlan = true;
    public ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        boolean fromCalendar = intent.getBooleanExtra("from_calendar", false);
        if (fromCalendar) {
            replaceFragment(new CalendarFragment());
            binding.bottomNavigationView.setSelectedItemId(R.id.CalendarNav);
        } else {
            replaceFragment(new ProfileFragment());
            binding.bottomNavigationView.setSelectedItemId(R.id.ProfileNav);
        }
            binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int itemId = item.getItemId();
                    if (itemId == R.id.HomeNav && selectedPlan) {
                        replaceFragment(new HomeFragment());
                    }
                    else if (itemId == R.id.CalendarNav && selectedPlan) {
                        replaceFragment(new CalendarFragment());
                    }
                    else if (itemId == R.id.ProfileNav) {
                        replaceFragment(new ProfileFragment());
                    }
                    else {
                        binding.bottomNavigationView.setSelectedItemId(R.id.ProfileNav);
                        Toast.makeText(MainActivity.this, "Please selected a travel plan.", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    return true;
                }
            });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}