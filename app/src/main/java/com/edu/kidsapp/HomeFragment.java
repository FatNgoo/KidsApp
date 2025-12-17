package com.edu.kidsapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
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
 * Duolingo-inspired design with direct game access
 * Features: Bright colors, game cards, progress tracking, motivational UI
 */
public class HomeFragment extends Fragment {

    // UI Components
    private MaterialCardView cardSchool, cardShop, cardMasterChef, cardDetective, 
                             cardWordWorkshop, cardMoreGames;
    private View btnSettings;

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
        
        // Add entrance animations
        animateEnter();
    }

    /**
     * Initialize all view components using findViewById
     */
    private void initializeViews(View view) {
        // Quick access
        cardSchool = view.findViewById(R.id.cardSchool);
        cardShop = view.findViewById(R.id.cardShop);
        
        // Game cards
        cardMasterChef = view.findViewById(R.id.cardMasterChef);
        cardDetective = view.findViewById(R.id.cardDetective);
        cardWordWorkshop = view.findViewById(R.id.cardWordWorkshop);
        cardMoreGames = view.findViewById(R.id.cardMoreGames);
        
        // Top bar
        btnSettings = view.findViewById(R.id.btnSettings);
    }

    /**
     * Set dummy data - No longer needed
     */
    private void setDummyData() {
        // Data removed as gold counter is removed
    }

    /**
     * Setup click listeners for all interactive elements
     */
    private void setupClickListeners() {
        // School Button - Navigate to School Lobby
        cardSchool.setOnClickListener(v -> {
            animateBounce(v);
            Navigation.findNavController(v).navigate(R.id.action_home_to_school_lobby);
        });

        // Shop Button
        cardShop.setOnClickListener(v -> {
            animateBounce(v);
            Navigation.findNavController(v).navigate(R.id.action_home_to_shop);
        });

        // MasterChef Game - Direct launch
        cardMasterChef.setOnClickListener(v -> {
            animateBounce(v);
            Intent intent = new Intent(requireContext(), CookingGameActivity.class);
            startActivity(intent);
        });

        // Detective Game - Direct launch
        cardDetective.setOnClickListener(v -> {
            animateBounce(v);
            Intent intent = new Intent(requireContext(), DetectiveGameActivity.class);
            startActivity(intent);
        });

        // Word Workshop - Coming soon
        cardWordWorkshop.setOnClickListener(v -> {
            animateBounce(v);
            Toast.makeText(requireContext(), "🎓 Word Workshop coming soon!", Toast.LENGTH_SHORT).show();
        });

        // More Games - Navigate to Game Lobby
        cardMoreGames.setOnClickListener(v -> {
            animateBounce(v);
            Navigation.findNavController(v).navigate(R.id.action_home_to_game_lobby);
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
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.92f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.92f);
        scaleDownX.setDuration(100);
        scaleDownY.setDuration(100);

        // Scale up animation with overshoot
        ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(view, "scaleX", 0.92f, 1.05f, 1.0f);
        ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(view, "scaleY", 0.92f, 1.05f, 1.0f);
        scaleUpX.setDuration(200);
        scaleUpY.setDuration(200);

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
     * Animate entrance of game cards with staggered delays
     */
    private void animateEnter() {
        View[] cards = {cardSchool, cardShop, cardMasterChef, cardDetective, 
                        cardWordWorkshop, cardMoreGames};
        
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] != null) {
                cards[i].setAlpha(0f);
                cards[i].setTranslationY(50f);
                
                cards[i].animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(400)
                    .setStartDelay(i * 80L)
                    .start();
            }
        }
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
                        Toast.makeText(requireContext(), "✅ Access Granted!", Toast.LENGTH_SHORT).show();
                        // TODO: Navigate to settings screen
                    } else {
                        Toast.makeText(requireContext(), "❌ Try Again!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
