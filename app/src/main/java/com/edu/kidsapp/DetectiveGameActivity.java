package com.edu.kidsapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * DetectiveGameActivity - Placeholder for Detective minigame
 * TODO: Implement actual detective game logic
 */
public class DetectiveGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detective_game);

        // Hide action bar for immersive game experience
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}
