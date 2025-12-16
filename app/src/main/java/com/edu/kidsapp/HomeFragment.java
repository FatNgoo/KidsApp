package com.edu.kidsapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.card.MaterialCardView;

/**
 * HomeFragment - Main screen for 4Kids English Learning App
 * Displays floating town concept with School, Shop, and Game Zone
 */
public class HomeFragment extends Fragment {

    // UI Components
    private MaterialCardView cardSchool, cardShop, cardGame;
    private View btnSettings;
    private TextView tvTicketCount, tvGoldCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Views
        initializeViews(view);

        // Set Dummy Data
        setDummyData();

        // Setup Click Listeners
        setupClickListeners();
    }

    /**
     * Initialize all view components using findViewById
     */
    private void initializeViews(View view) {
        cardSchool = view.findViewById(R.id.cardSchool);
        cardShop = view.findViewById(R.id.cardShop);
        cardGame = view.findViewById(R.id.cardGame);
        btnSettings = view.findViewById(R.id.btnSettings);
        tvTicketCount = view.findViewById(R.id.tvTicketCount);
        tvGoldCount = view.findViewById(R.id.tvGoldCount);
    }

    /**
     * Set dummy data for tickets and gold
     */
    private void setDummyData() {
        tvTicketCount.setText("3");
        tvGoldCount.setText("150");
    }

    /**
     * Setup click listeners for all interactive elements
     */
    private void setupClickListeners() {
        // School Button - Navigate to Adventure Map
        cardSchool.setOnClickListener(v -> {
            animateBounce(v);
            Navigation.findNavController(v).navigate(R.id.action_home_to_map);
        });

        // Shop Button
        cardShop.setOnClickListener(v -> {
            animateBounce(v);
            Toast.makeText(requireContext(), "Loading Shop...", Toast.LENGTH_SHORT).show();
        });

        // Game Zone Button
        cardGame.setOnClickListener(v -> {
            animateBounce(v);
            Toast.makeText(requireContext(), "Loading Games...", Toast.LENGTH_SHORT).show();
        });

        // Settings Button
        btnSettings.setOnClickListener(v -> {
            animateBounce(v);
            showSettingsDialog();
        });
    }

    /**
     * Animate bounce effect: scales view down to 0.9f and back to 1.0f
     * Duration: 150ms each way (300ms total)
     *
     * @param view The view to animate
     */
    private void animateBounce(View view) {
        // Scale down animation
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.9f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.9f);
        scaleDownX.setDuration(150);
        scaleDownY.setDuration(150);

        // Scale up animation
        ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(view, "scaleX", 0.9f, 1.0f);
        ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(view, "scaleY", 0.9f, 1.0f);
        scaleUpX.setDuration(150);
        scaleUpY.setDuration(150);

        // Combine animations
        AnimatorSet scaleDown = new AnimatorSet();
        scaleDown.play(scaleDownX).with(scaleDownY);

        AnimatorSet scaleUp = new AnimatorSet();
        scaleUp.play(scaleUpX).with(scaleUpY);

        // Play in sequence
        AnimatorSet bounce = new AnimatorSet();
        bounce.play(scaleDown).before(scaleUp);
        bounce.start();
    }

    /**
     * Show settings dialog with math problem authentication
     * Problem: 10 + 5 = ?
     * Correct answer: 15
     */
    private void showSettingsDialog() {
        // Create input field
        final EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setHint("Enter answer");

        // Build dialog
        new AlertDialog.Builder(requireContext())
                .setTitle("Settings Access")
                .setMessage("Solve this problem to access settings:\n\n10 + 5 = ?")
                .setView(input)
                .setPositiveButton("Submit", (dialog, which) -> {
                    String answer = input.getText().toString().trim();
                    if (answer.equals("15")) {
                        Toast.makeText(requireContext(), "Access Granted ✓", Toast.LENGTH_SHORT).show();
                        // TODO: Navigate to settings screen
                    } else {
                        Toast.makeText(requireContext(), "Try Again ✗", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
