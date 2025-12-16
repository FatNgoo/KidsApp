package com.edu.kidsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * DetectiveGameActivity - Tap to find hidden clues game
 * Players must find 3 hidden objects in a crime scene
 */
public class DetectiveGameActivity extends AppCompatActivity {

    private TextView tvScore;
    private ImageView clueFootprint, clueMagnifier, clueKey;

    private int foundCount = 0;
    private final int totalClues = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detective_game);

        // Hide action bar for immersive game experience
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Initialize views
        tvScore = findViewById(R.id.tv_score);
        clueFootprint = findViewById(R.id.clue_footprint);
        clueMagnifier = findViewById(R.id.clue_magnifier);
        clueKey = findViewById(R.id.clue_key);

        // Setup hidden items
        setupHiddenItem(clueFootprint, "Footprint");
        setupHiddenItem(clueMagnifier, "Magnifier");
        setupHiddenItem(clueKey, "Key");
    }

    /**
     * Setup click listener for each hidden clue
     */
    private void setupHiddenItem(ImageView clueView, String clueName) {
        clueView.setOnClickListener(v -> {
            // Make the clue disappear (simulate collecting)
            v.setVisibility(View.GONE);

            // Increment found count
            foundCount++;

            // Update score display
            tvScore.setText("Clues Found: " + foundCount + "/" + totalClues);

            // Show toast feedback
            Toast.makeText(this, "Found: " + clueName + "!", Toast.LENGTH_SHORT).show();

            // Check win condition
            if (foundCount == totalClues) {
                showWinDialog();
            }
        });
    }

    /**
     * Show win dialog when all clues are found
     */
    private void showWinDialog() {
        new AlertDialog.Builder(this)
                .setTitle("🔎 Case Closed!")
                .setMessage("You found all the clues!\n\nYou represent the best detective!")
                .setPositiveButton("Play Again", (dialog, which) -> {
                    // Reset game
                    resetGame();
                })
                .setNegativeButton("Exit", (dialog, which) -> {
                    // Close activity
                    finish();
                })
                .setCancelable(false)
                .show();
    }

    /**
     * Reset game state for replay
     */
    private void resetGame() {
        foundCount = 0;
        tvScore.setText("Clues Found: 0/" + totalClues);
        clueFootprint.setVisibility(View.VISIBLE);
        clueMagnifier.setVisibility(View.VISIBLE);
        clueKey.setVisibility(View.VISIBLE);
    }
}
