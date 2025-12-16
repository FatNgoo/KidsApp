package com.edu.kidsapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

/**
 * SplashActivity - Welcome screen shown on app launch
 * Displays app logo for 3 seconds before navigating to MainActivity
 */
public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Hide Action Bar for clean splash screen
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Navigate to MainActivity after delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start MainActivity
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                
                // Finish splash so user cannot go back
                finish();
            }
        }, SPLASH_DURATION);
    }
}
