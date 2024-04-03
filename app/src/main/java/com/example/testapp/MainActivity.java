package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.testapp.MainFragments.CalendarFragment;
import com.example.testapp.databinding.ActivityMainBinding;
import com.example.testapp.home_fragment.HomeFragment;
import com.example.testapp.profile_fragment.ProfileFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    public ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        boolean fromCalendar = intent.getBooleanExtra("from_calendar", false);
        System.out.println(fromCalendar);
        if (fromCalendar) {
            replaceFragment(new CalendarFragment());
            binding.bottomNavigationView.setSelectedItemId(R.id.CalendarNav);
        } else {
            replaceFragment(new HomeFragment());
        }
        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.HomeNav) {
                    replaceFragment(new HomeFragment());
                }
                else if (itemId == R.id.CalendarNav) {
                    replaceFragment(new CalendarFragment());
                }
                else if (itemId == R.id.ProfileNav) {
                    replaceFragment(new ProfileFragment());
                }
                else {
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