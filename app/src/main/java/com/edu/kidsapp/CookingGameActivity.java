package com.edu.kidsapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * CookingGameActivity - Placeholder for Master Chef minigame
 * TODO: Implement actual cooking game logic
 */
public class CookingGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking_game);

        // Hide action bar for immersive game experience
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}
