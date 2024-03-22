package com.example.timetravelfrontend;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity {

    private LinearLayout textBubbleBase;
    private LinearLayout dropdownContentContainer;
    private boolean isExpanded; // Flag to track expansion state
    private Button toggleDropdown;
    private int buttonCollapsedState = 1;

//    private Resources resources = getResources();
//    private Context context = getApplicationContext();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textBubbleBase = findViewById(R.id.text_bubble_base);
        dropdownContentContainer = findViewById(R.id.dropdown_content_container);
        toggleDropdown = findViewById(R.id.toggle_dropdown);
        isExpanded = false; // Initially collapsed

        toggleDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Dropdown", "Count the number of me to see how many button press");
                toggleDropdownContent(v);
            }
        });
    }


    private void toggleDropdownContent(View v) {
        if(v instanceof Button) {
            Button dropdownButton = (Button) v;
            int drawableCollapsedId = R.drawable.ui_element_dropdown_button_collapsed;
            int drawableExpandedId = R.drawable.ui_element_dropdown_button_expanded;

            Drawable drawableCollapsedObject = ContextCompat.getDrawable(this, drawableCollapsedId);
            Drawable drawableExpandedObject = ContextCompat.getDrawable(this, drawableExpandedId);
            Drawable background = dropdownButton.getBackground();

            if (buttonCollapsedState == 1) {
                Log.d("Dropdown", "Dropdown button is collapsed");
                dropdownButton.setBackground(drawableExpandedObject);
                buttonCollapsedState = 0;
                Log.d("Dropdown", "Dropdown button is set to expanded" + buttonCollapsedState);
            } else {
                Log.d("Dropdown", "Dropdown button is expanded");
                dropdownButton.setBackground(drawableCollapsedObject);
                buttonCollapsedState = 1;
                Log.d("Dropdown", "Dropdown button is set to collapsed" + buttonCollapsedState);
            }
        }

        if (isExpanded) {
            collapseDropdownContent(v);
        } else {
            expandDropdownContent(v);
        }
        isExpanded = !isExpanded; // Toggle flag based on action
    }



    private void expandDropdownContent(View v) {



        dropdownContentContainer.setVisibility(View.VISIBLE); // Show container
        // Add linear animation for expansion (explained later)



        dropdownContentContainer.animate()
                .setDuration(300) // Adjust duration as needed (in milliseconds)
                .alpha(1f) // Animate opacity from 0 to 1 (fully visible)
                .scaleY(1f) // Animate scaleY from 0 to 1 (fully expanded)
                .setStartDelay(0) // Adjust delay if needed (in milliseconds)
                .setInterpolator(new LinearInterpolator()) // Use linear interpolation
                .start();
    }



    private void collapseDropdownContent(View v) {
        dropdownContentContainer.animate()
                .setDuration(300) // Adjust duration as needed (in milliseconds)
                .alpha(0f) // Animate opacity from 1 to 0 (fully invisible)
                .scaleY(0f) // Animate scaleY from 1 to 0 (fully collapsed)
                .setStartDelay(0) // Adjust delay if needed (in milliseconds)
                .setInterpolator(new LinearInterpolator()) // Use linear interpolation
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        dropdownContentContainer.setVisibility(View.GONE);
                    }
                })
                .start();
    }

}