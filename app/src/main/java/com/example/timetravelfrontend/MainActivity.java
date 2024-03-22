package com.example.timetravelfrontend;


import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import java.util.HashMap;


class Event{
    private LinearLayout textBubbleContainer;
    private LinearLayout collapsedContainer;
    private AppCompatButton dropdownButton;
    private TextView collapsedText;
    private LinearLayout expandedContainer;
    private TextView expandedText;

    private Button toggleDropdown;
    private boolean buttonExpanded = false;
    private static int eventCount = 0;
    private static HashMap<String, Integer> textBubbleContainerReference = new HashMap<>();


    private String timeData;
    private String dropdownMessageData;
    private Context activity;

    Event(String timeData, String dropdownMessageData, Context activity){
        this.timeData = timeData;
        this.dropdownMessageData = dropdownMessageData;
        this.activity = activity;
    }

    public LinearLayout createEvent(){
        Typeface varelaFont = Typeface.createFromAsset(activity.getAssets(), "fonts/" + "varela_round_regular.ttf");


        //start text bubble container
        textBubbleContainer = new LinearLayout(activity);
        textBubbleContainer.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)); // Set width and height
        textBubbleContainer.setOrientation(LinearLayout.VERTICAL); // Set orientation
        textBubbleContainer.setGravity(Gravity.CENTER); // Center the layout

        eventCount++;
        Integer uniqueId = View.generateViewId();
        textBubbleContainer.setId(uniqueId); // Set the ID
        textBubbleContainerReference.put("textBubbleContainer" + eventCount, uniqueId);
        textBubbleContainer.setTag("textBubbleContainer" + eventCount);


        textBubbleContainer.setBackgroundResource(R.drawable.event_bubble_base); // Set background

         // Set a bottom margin (optional)
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textBubbleContainer.getLayoutParams();
        params.setMargins(dptopx(0), dptopx(0), dptopx(0), dptopx(10)); // 10dp margin at the bottom
        textBubbleContainer.setLayoutParams(params);




        //start collapsed container
        collapsedContainer = new LinearLayout(activity);
        collapsedContainer.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)); // Set width and height
//        textBubbleContainer.setOrientation(LinearLayout.HORIZONTAL); // Set orientation
        collapsedContainer.setGravity(Gravity.CENTER); // Center the layout
        collapsedContainer.setPadding(dptopx(10), dptopx(5), dptopx(10), dptopx(0));


        //start drop down button
        dropdownButton = new AppCompatButton(activity);

        dropdownButton.setLayoutParams(new LinearLayout.LayoutParams(dptopx(15), dptopx(15))); // Set layout params
        dropdownButton.setBackgroundResource(R.drawable.ui_element_dropdown_button_collapsed); // Set background
        dropdownButton.setPadding(dptopx(10), dptopx(5), dptopx(10), dptopx(0)); // Set padding
        dropdownButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.d("Dropdown",textBubbleContainer.getTag() + " has been pressed.");

                        int drawableCollapsedId = R.drawable.ui_element_dropdown_button_collapsed;
                        int drawableExpandedId = R.drawable.ui_element_dropdown_button_expanded;

                        Drawable drawableCollapsedObject = ContextCompat.getDrawable(activity, drawableCollapsedId);
                        Drawable drawableExpandedObject = ContextCompat.getDrawable(activity, drawableExpandedId);
                        Drawable background = dropdownButton.getBackground();

                        if (!buttonExpanded) {
                            dropdownButton.setBackground(drawableExpandedObject);
                            showDropdown(expandedContainer);
                            buttonExpanded = true;
                        }
                        else {
                            dropdownButton.setBackground(drawableCollapsedObject);
                            hideDropdown(expandedContainer);
                            buttonExpanded = false;
                        }
                    }
                });

        //end drop down button

        //start collapsed text
        collapsedText = new TextView(activity);

        collapsedText.setLayoutParams(new LinearLayout.LayoutParams(
                dptopx(315), // Fixed width in dp but it wants px
                LinearLayout.LayoutParams.WRAP_CONTENT)); // Wrap content height
        collapsedText.setText(timeData); // Set the text
        collapsedText.setTypeface(varelaFont);
        //end collapsed text

        // Add the dropdown and collapsed text to collapsed container (assuming you have a reference)
        collapsedContainer.addView(dropdownButton);
        collapsedContainer.addView(collapsedText);

        //end collapsed container

        //start expanded container

        expandedContainer = new LinearLayout(activity);
        expandedContainer.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        expandedContainer.setOrientation(LinearLayout.VERTICAL);
        expandedContainer.setVisibility(View.GONE);  // Initially invisible

        //start expanded text
        expandedText = new TextView(activity);
        expandedText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)); // Set layout parameters
        expandedText.setText(dropdownMessageData);
        expandedText.setTypeface(varelaFont); // Assuming font file is in assets
        //end expanded text
        expandedContainer.addView(expandedText);

        //end expanded container



        textBubbleContainer.addView(collapsedContainer);
        textBubbleContainer.addView(expandedContainer);
        //end text bubble container


        return textBubbleContainer;

    }

    public void showDropdown(LinearLayout expandedContainer){
        expandedContainer.setVisibility(View.VISIBLE); // Show container
        // Add linear animation for expansion (explained later)



        expandedContainer.animate()
                .setDuration(300) // Adjust duration as needed (in milliseconds)
                .alpha(1f) // Animate opacity from 0 to 1 (fully visible)
                .scaleY(1f) // Animate scaleY from 0 to 1 (fully expanded)
                .setStartDelay(0) // Adjust delay if needed (in milliseconds)
                .setInterpolator(new LinearInterpolator()) // Use linear interpolation
                .start();
    }

    public void hideDropdown(LinearLayout expandedContainer){
        expandedContainer.animate()
                .setDuration(300) // Adjust duration as needed (in milliseconds)
                .alpha(0f) // Animate opacity from 1 to 0 (fully invisible)
                .scaleY(0f) // Animate scaleY from 1 to 0 (fully collapsed)
                .setStartDelay(0) // Adjust delay if needed (in milliseconds)
                .setInterpolator(new LinearInterpolator()) // Use linear interpolation
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        expandedContainer.setVisibility(View.GONE);
                    }
                })
                .start();
    }

    public int dptopx(int dp){
        int buttonSizeInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, activity.getResources().getDisplayMetrics());

        return buttonSizeInPx;
    }



}
public class MainActivity extends AppCompatActivity {
//
//    private LinearLayout textBubbleBase;
//    private LinearLayout dropdownContentContainer;
//    private boolean isExpanded; // Flag to track expansion state
//    private Button toggleDropdown;
//    private int buttonCollapsedState = 1;

//    private Resources resources = getResources();
//    private Context context = getApplicationContext();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Event nineAm = new Event("0900-1000", "Let us have a blast", this);

        LinearLayout mainContainer = findViewById(R.id.mainContainer);


        mainContainer.addView(nineAm.createEvent());



//        textBubbleBase = findViewById(R.id.text_bubble_base);
//        dropdownContentContainer = findViewById(R.id.dropdown_content_container);
//        toggleDropdown = findViewById(R.id.toggle_dropdown);
//        isExpanded = false; // Initially collapsed
//
//        toggleDropdown.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("Dropdown", "Count the number of me to see how many button press");
//                toggleDropdownContent(v);
//            }
//        });
//    }
//
//
//    private void toggleDropdownContent(View v) {
//        if(v instanceof Button) {
//            Button dropdownButton = (Button) v;
//            int drawableCollapsedId = R.drawable.ui_element_dropdown_button_collapsed;
//            int drawableExpandedId = R.drawable.ui_element_dropdown_button_expanded;
//
//            Drawable drawableCollapsedObject = ContextCompat.getDrawable(this, drawableCollapsedId);
//            Drawable drawableExpandedObject = ContextCompat.getDrawable(this, drawableExpandedId);
//            Drawable background = dropdownButton.getBackground();
//
//            if (buttonCollapsedState == 1) {
//                Log.d("Dropdown", "Dropdown button is collapsed");
//                dropdownButton.setBackground(drawableExpandedObject);
//                buttonCollapsedState = 0;
//                Log.d("Dropdown", "Dropdown button is set to expanded" + buttonCollapsedState);
//            } else {
//                Log.d("Dropdown", "Dropdown button is expanded");
//                dropdownButton.setBackground(drawableCollapsedObject);
//                buttonCollapsedState = 1;
//                Log.d("Dropdown", "Dropdown button is set to collapsed" + buttonCollapsedState);
//            }
//        }
//
//        if (isExpanded) {
//            collapseDropdownContent(v);
//        } else {
//            expandDropdownContent(v);
//        }
//        isExpanded = !isExpanded; // Toggle flag based on action
//    }
//
//
//
//    private void expandDropdownContent(View v) {
//
//
//
//        dropdownContentContainer.setVisibility(View.VISIBLE); // Show container
//        // Add linear animation for expansion (explained later)
//
//
//
//        dropdownContentContainer.animate()
//                .setDuration(300) // Adjust duration as needed (in milliseconds)
//                .alpha(1f) // Animate opacity from 0 to 1 (fully visible)
//                .scaleY(1f) // Animate scaleY from 0 to 1 (fully expanded)
//                .setStartDelay(0) // Adjust delay if needed (in milliseconds)
//                .setInterpolator(new LinearInterpolator()) // Use linear interpolation
//                .start();
//    }
//
//
//
//    private void collapseDropdownContent(View v) {
//        dropdownContentContainer.animate()
//                .setDuration(300) // Adjust duration as needed (in milliseconds)
//                .alpha(0f) // Animate opacity from 1 to 0 (fully invisible)
//                .scaleY(0f) // Animate scaleY from 1 to 0 (fully collapsed)
//                .setStartDelay(0) // Adjust delay if needed (in milliseconds)
//                .setInterpolator(new LinearInterpolator()) // Use linear interpolation
//                .withEndAction(new Runnable() {
//                    @Override
//                    public void run() {
//                        dropdownContentContainer.setVisibility(View.GONE);
//                    }
//                })
//                .start();
    }

}