package com.example.testapp;

import android.content.Intent;
import android.media.MediaActionSound;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingPage extends AppCompatActivity {
    Animation topAnim, bottomAnim;
    ImageView image;
    ImageView image_2;
    MediaPlayer whoosh;
    int flag = 0;
    private static int SPLASH_SCREEN = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(LoadingPage.this, LoginPage.class));

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.loadingscreen);
        whoosh = MediaPlayer.create(LoadingPage.this, R.raw.little_whoosh);
        whoosh.start();

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        image = findViewById(R.id.action_image);
        image_2 = findViewById(R.id.text_image);
        image.setAnimation(topAnim);
        image_2.setAnimation(bottomAnim);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(LoadingPage.this, LoginPage.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_SCREEN);
        }
    }
